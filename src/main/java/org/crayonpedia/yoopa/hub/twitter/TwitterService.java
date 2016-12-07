package org.crayonpedia.yoopa.hub.twitter;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Created by ceefour on 07/12/2016.
 */
@Service
@RestController
@RequestMapping("api/twitter")
public class TwitterService {
    private static final Logger log = LoggerFactory.getLogger(TwitterService.class);

    @Autowired
    private TwitterAppRepository twitterAppRepo;
    @Autowired
    private TwitterAuthzRepository twitterAuthzRepo;

    public static class VerifiedRequest {
        private String token;
        private String tokenSecret;
        private String verifier;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public String getVerifier() {
            return verifier;
        }

        public void setVerifier(String verifier) {
            this.verifier = verifier;
        }
    }

    @GetMapping("requestToken/{twitterAppId}")
    public RequestToken getRequestToken(@PathVariable("twitterAppId") String twitterAppId) throws TwitterException {
        final TwitterApp app = twitterAppRepo.findOne(twitterAppId);
        final Configuration config = new ConfigurationBuilder().setOAuthConsumerKey(app.getApiKey())
                .setOAuthConsumerSecret(app.getApiSecret())
                .build();
        final Twitter twitter = new TwitterFactory(config).getInstance();
        final RequestToken requestToken = twitter.getOAuthRequestToken();
//        final String authorizationUrl = requestToken.getAuthorizationURL();
//        final TwitterAuthzRequest authzRequest = new TwitterAuthzRequest();
//        authzRequest.setRequestToken(requestToken.getToken());
        return requestToken;
    }

    @PostMapping("authorize/{twitterAppId}")
    public TwitterAuthz authorize(@PathVariable("twitterAppId") String twitterAppId, @RequestBody VerifiedRequest verified) throws TwitterException {
        final TwitterApp app = twitterAppRepo.findOne(twitterAppId);
        final Configuration config = new ConfigurationBuilder().setOAuthConsumerKey(app.getApiKey())
                .setOAuthConsumerSecret(app.getApiSecret())
                .build();
        final Twitter twitter = new TwitterFactory(config).getInstance();
        log.debug("token={} tokenSecret={} verifier={}", verified.getToken(), verified.getTokenSecret(), verified.getVerifier());
        final RequestToken requestToken = new RequestToken(verified.getToken(), verified.getTokenSecret());
        final AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verified.getVerifier());

        twitter.setOAuthAccessToken(accessToken);
//        final User twitterUser = twitter.showUser(twitter.getId());

        TwitterAuthz authz = new TwitterAuthz();
        authz.setTwitterApp(app);
        authz.setAccessToken(accessToken.getToken());
        authz.setAccessTokenSecret(accessToken.getTokenSecret());
        authz.setUserId(accessToken.getUserId());
        authz.setScreenName(accessToken.getScreenName());
        authz.setCreationTime(OffsetDateTime.now());
        authz = twitterAuthzRepo.save(authz);

        return authz;
    }

    /**
     * Get recent tweets from specified Twitter list.
     */
    @GetMapping("tweetsFromList")
    public ResponseList<Status> getTweetsFromList(@RequestParam("authzId") int authzId,
                                    @RequestParam("listOwner") String listOwner,
                                    @RequestParam("listSlug") String listSlug) throws TwitterException {
        final TwitterAuthz authz = twitterAuthzRepo.findOne(authzId);
        final TwitterApp app = authz.getTwitterApp();
        final Configuration config = new ConfigurationBuilder().setOAuthConsumerKey(app.getApiKey())
                .setOAuthConsumerSecret(app.getApiSecret())
                .setOAuthAccessToken(authz.getAccessToken())
                .setOAuthAccessTokenSecret(authz.getAccessTokenSecret())
                .build();
        final Twitter twitter = new TwitterFactory(config).getInstance();

        final ResponseList<Status> tweets = twitter.list().getUserListStatuses(listOwner, listSlug, new Paging(1, 100));
        return tweets;
    }

    /**
     * Get any tweet from specified Twitter list.
     */
    @GetMapping("anyTweetFromList")
    public Status getAnyTweetFromList(@RequestParam("authzId") int authzId,
                                      @RequestParam("listOwner") String listOwner,
                                      @RequestParam("listSlug") String listSlug) throws TwitterException {
        final TwitterAuthz authz = twitterAuthzRepo.findOne(authzId);
        final TwitterApp app = authz.getTwitterApp();
        final Configuration config = new ConfigurationBuilder().setOAuthConsumerKey(app.getApiKey())
                .setOAuthConsumerSecret(app.getApiSecret())
                .setOAuthAccessToken(authz.getAccessToken())
                .setOAuthAccessTokenSecret(authz.getAccessTokenSecret())
                .build();
        final Twitter twitter = new TwitterFactory(config).getInstance();

        final ResponseList<Status> tweets = twitter.list().getUserListStatuses(listOwner, listSlug, new Paging(1, 100));
        final int idx = RandomUtils.nextInt(0, tweets.size());
        final Status anyTweet = tweets.get(idx);
        return anyTweet;
    }

    /**
     * Get any tweet from specified Twitter list, as oEmbed.
     */
    @GetMapping("anyTweetFromList.oembed")
    public Map<String, Object> getAnyTweetFromListAsOEmbed(
            @RequestParam("authzId") int authzId,
                                      @RequestParam("listOwner") String listOwner,
                                      @RequestParam("listSlug") String listSlug) throws TwitterException {
        final TwitterAuthz authz = Preconditions.checkNotNull(twitterAuthzRepo.findOne(authzId),
                "Cannot find TwitterAuthz %s", authzId);
        final TwitterApp app = Preconditions.checkNotNull(authz.getTwitterApp(),
                "Cannot get TwitterApp for TwitterAuthz %s", authzId);
        final Configuration config = new ConfigurationBuilder()
                .setOAuthConsumerKey(app.getApiKey())
                .setOAuthConsumerSecret(app.getApiSecret())
                .setOAuthAccessToken(authz.getAccessToken())
                .setOAuthAccessTokenSecret(authz.getAccessTokenSecret())
                .build();
        final Twitter twitter = new TwitterFactory(config).getInstance();

        final ResponseList<Status> tweets = twitter.list().getUserListStatuses(listOwner, listSlug, new Paging(1, 100));
        final int idx = RandomUtils.nextInt(0, tweets.size());
        final Status anyTweet = tweets.get(idx);
        final String twitterUrl = "https://twitter.com/" + anyTweet.getUser().getScreenName() + "/statuses/" + anyTweet.getId();

        final RestTemplate rest = new RestTemplate();
        final Map<String, Object> oembed = rest.getForObject("https://publish.twitter.com/oembed?url={url}&omit_script=true", Map.class,
                ImmutableMap.of("url", twitterUrl));
        return oembed;
    }

}
