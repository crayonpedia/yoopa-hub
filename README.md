# Yoopa Hub

## Deployment

Environment:

1. `SPRING_PROFILES_ACTIVE=production`
2. `SPRING_DATASOURCE_URL`

## Authentication

Untuk setiap REST API call, authentication dengan schema `Basic` dibutuhkan.

User dan password _default_ dapat dilihat di `application.yml` .

## Menambahkan TwitterApp

Sebelum bisa otorisasi user, sebuah app perlu ditambahkan.
Silakan login di https://apps.twitter.com/ untuk mendapatkan API Key and API Secret Anda.

Cara menambahkan via [Postman](http://getpostman.com/) :

```
POST {{baseUri}}api/twitterApps
Content-Type: application/json
```

```json
{
    "id": "lumen",
    "name": "Lumen Robot Friend",
    "apiKey": "*",
    "apiSecret": "*"
}
```

## Menambahkan TwitterAuthorization

Agar aplikasi dapat mengakses Twitter API, maka selain TwitterApp, dibutuhkan juga
otorisasi untuk minimal sebuah user.

Apabila sudah memiliki access token dan access token secret, bisa langsung lanjut
ke bagian selanjutnya. Tapi bila belum, dapatkan dulu sebagai berikut via [Postman](http://getpostman.com/) :

```
GET {{baseUri}}api/twitter/requestToken/lumen
```

Hasilnya:

```json
{
  "token": "uiHkXwAAAAAAdxTZAAABWNdjUU0",
  "tokenSecret": "*",
  "authenticationURL": "https://api.twitter.com/oauth/authenticate?oauth_token=uiHkXwAAAAAAdxTZAAABWNdjUU0",
  "authorizationURL": "https://api.twitter.com/oauth/authorize?oauth_token=uiHkXwAAAAAAdxTZAAABWNdjUU0"
}
```

Lalu buka `authorizationURL` di browser. Bila konfigurasi app Anda menggunakan redirect,
maka akan redirect ke, misal: http://lumen.lskk.org/?oauth_token=gmWMigAAAAAAdxTZAAABWNdmCzc&oauth_verifier=x

Tambahkan authorization dengan memasukkan request token, request token secret,
dan verifier.

```
POST {{baseUri}}api/twitter/authorize/lumen
Content-Type: application/json
```

```json
{
    "requestToken": {
        "token": "uiHkXwAAAAAAdxTZAAABWNdjUU0",
        "tokenSecret": "*",
        "authenticationURL": "https://api.twitter.com/oauth/authenticate?oauth_token=uiHkXwAAAAAAdxTZAAABWNdjUU0",
        "authorizationURL": "https://api.twitter.com/oauth/authorize?oauth_token=uiHkXwAAAAAAdxTZAAABWNdjUU0"
    },
    "verifier": "x"
}
```

Hasilnya:

```json
{
  "userId": 2799195116,
  "screenName": "LumenRobot",
  "creationTime": "2016-12-07T11:25:23+07:00",
  "accessToken": "x",
  "accessTokenSecret": "x",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/twitterAuthorizations/1"
    },
    "twitterAuthorization": {
      "href": "http://localhost:8080/api/twitterAuthorizations/1"
    },
    "twitterApp": {
      "href": "http://localhost:8080/api/twitterAuthorizations/1/twitterApp"
    }
  }
}
```

Alternatif: Untuk langsung memasukkan `TwitterAuthz` (biasanya disalin dari server dev):

```
POST {{baseUri}}api/twitterAuthzes
Content-Type: application/json
```

```json
{
  "twitterApp": "/api/twitterApps/lumen",
  "userId": 2799195116,
  "screenName": "LumenRobot",
  "creationTime": "2016-12-07T11:25:23+07:00",
  "accessToken": "x",
  "accessTokenSecret": "x"
}
```

## Get Tweets from List

```
GET {{baseUri}}api/twitter/tweetsFromList?authzId=1&listOwner=hendybippo&listSlug=pk85
```

Hasilnya:

```json
[
  {
    "rateLimitStatus": null,
    "accessLevel": 0,
    "createdAt": 1481084166000,
    "id": 806351554974388224,
    "text": "Motormu kan udah gedeeeeeee https://t.co/haHuJnlCBC",
    "source": "<a href=\"http://twitter.com/download/android\" rel=\"nofollow\">Twitter for Android</a>",
    "inReplyToStatusId": -1,
    "inReplyToUserId": -1,
    "favoriteCount": 0,
    "inReplyToScreenName": null,
    "geoLocation": null,
    "place": null,
    "retweetCount": 0,
    "lang": "in",
    "retweetedStatus": null,
    "userMentionEntities": [],
    "hashtagEntities": [],
    "mediaEntities": [],
    "extendedMediaEntities": [],
    "symbolEntities": [],
    "currentUserRetweetId": -1,
    "scopes": null,
    "user": {
      "rateLimitStatus": null,
      "accessLevel": 0,
      "id": 74998551,
      "name": "Mrs. 中本",
      "email": null,
      "screenName": "agrully",
      "location": "DE",
      "description": "Der verlorene Wanderer · Brondong Lover · Tukang Mager · Bukan Desainer · #NCTtrash · #EXOtrash · #YuJaeYong · 도정김이 · 中本 · 🐧🍑⚽🐰❄🎶 · Alpha Female Wanna Be",
      "descriptionURLEntities": [],
      "url": "https://t.co/rQITbBcDab",
      "followersCount": 571,
      "status": null,
      "profileBackgroundColor": "000000",
      "profileTextColor": "000000",
      "profileLinkColor": "050505",
      "profileSidebarFillColor": "000000",
      "profileSidebarBorderColor": "000000",
      "profileUseBackgroundImage": false,
      "showAllInlineMedia": false,
      "friendsCount": 160,
      "createdAt": 1253190214000,
      "favouritesCount": 2808,
      "utcOffset": 25200,
      "timeZone": "Asia/Jakarta",
      "profileBackgroundImageUrlHttps": "https://abs.twimg.com/images/themes/theme14/bg.gif",
      "profileBackgroundTiled": false,
      "lang": "en",
      "statusesCount": 98856,
      "translator": false,
      "listedCount": 13,
      "withheldInCountries": null,
      "protected": false,
      "defaultProfile": false,
      "verified": false,
      "geoEnabled": true,
      "urlentity": {
        "start": 0,
        "end": 23,
        "url": "https://t.co/rQITbBcDab",
        "expandedURL": "http://aremanita-licek.blogspot.com",
        "displayURL": "aremanita-licek.blogspot.com",
        "text": "https://t.co/rQITbBcDab"
      },
      "miniProfileImageURL": "http://pbs.twimg.com/profile_images/802832704169381888/douDUYe__mini.jpg",
      "profileBannerURL": "https://pbs.twimg.com/profile_banners/74998551/1479001416/web",
      "biggerProfileImageURLHttps": "https://pbs.twimg.com/profile_images/802832704169381888/douDUYe__bigger.jpg",
      "profileBackgroundImageURL": "http://abs.twimg.com/images/themes/theme14/bg.gif",
      "contributorsEnabled": false,
      "defaultProfileImage": false,
      "originalProfileImageURLHttps": "https://pbs.twimg.com/profile_images/802832704169381888/douDUYe_.jpg",
      "profileImageURLHttps": "https://pbs.twimg.com/profile_images/802832704169381888/douDUYe__normal.jpg",
      "profileBannerIPadRetinaURL": "https://pbs.twimg.com/profile_banners/74998551/1479001416/ipad_retina",
      "followRequestSent": false,
      "profileBannerRetinaURL": "https://pbs.twimg.com/profile_banners/74998551/1479001416/web_retina",
      "miniProfileImageURLHttps": "https://pbs.twimg.com/profile_images/802832704169381888/douDUYe__mini.jpg",
      "profileBannerMobileURL": "https://pbs.twimg.com/profile_banners/74998551/1479001416/mobile",
      "profileImageURL": "http://pbs.twimg.com/profile_images/802832704169381888/douDUYe__normal.jpg",
      "profileBannerIPadURL": "https://pbs.twimg.com/profile_banners/74998551/1479001416/ipad",
      "profileBannerMobileRetinaURL": "https://pbs.twimg.com/profile_banners/74998551/1479001416/mobile_retina",
      "biggerProfileImageURL": "http://pbs.twimg.com/profile_images/802832704169381888/douDUYe__bigger.jpg",
      "originalProfileImageURL": "http://pbs.twimg.com/profile_images/802832704169381888/douDUYe_.jpg"
    },
    "withheldInCountries": null,
    "quotedStatus": {
      "rateLimitStatus": null,
      "accessLevel": 0,
      "createdAt": 1481084000000,
      "id": 806350858149593088,
      "text": "aku juga mau punya moge :(",
      "source": "<a href=\"https://about.twitter.com/products/tweetdeck\" rel=\"nofollow\">TweetDeck</a>",
      "inReplyToStatusId": -1,
      "inReplyToUserId": -1,
      "favoriteCount": 0,
      "inReplyToScreenName": null,
      "geoLocation": null,
      "place": null,
      "retweetCount": 0,
      "lang": "in",
      "retweetedStatus": null,
      "userMentionEntities": [],
      "hashtagEntities": [],
      "mediaEntities": [],
      "extendedMediaEntities": [],
      "symbolEntities": [],
      "currentUserRetweetId": -1,
      "scopes": null,
      "user": {
        "rateLimitStatus": null,
        "accessLevel": 0,
        "id": 76319318,
        "name": "徳山",
        "email": null,
        "screenName": "bongolwinter",
        "location": "Tesseract",
        "description": "Are you embarking the path of carnage?",
        "descriptionURLEntities": [],
        "url": "https://t.co/QWHFmCkjFh",
        "followersCount": 485,
        "status": null,
        "profileBackgroundColor": "000000",
        "profileTextColor": "000000",
        "profileLinkColor": "000000",
        "profileSidebarFillColor": "91C43A",
        "profileSidebarBorderColor": "000000",
        "profileUseBackgroundImage": true,
        "showAllInlineMedia": false,
        "friendsCount": 196,
        "createdAt": 1253622387000,
        "favouritesCount": 57,
        "utcOffset": 25200,
        "timeZone": "Jakarta",
        "profileBackgroundImageUrlHttps": "https://pbs.twimg.com/profile_background_images/689475344793862144/n4dBUE1Q.jpg",
        "profileBackgroundTiled": true,
        "lang": "en",
        "statusesCount": 77903,
        "translator": false,
        "listedCount": 14,
        "withheldInCountries": null,
        "protected": false,
        "defaultProfile": false,
        "verified": false,
        "geoEnabled": true,
        "urlentity": {
          "start": 0,
          "end": 23,
          "url": "https://t.co/QWHFmCkjFh",
          "expandedURL": "http://bongolwinter.tumblr.com",
          "displayURL": "bongolwinter.tumblr.com",
          "text": "https://t.co/QWHFmCkjFh"
        },
        "miniProfileImageURL": "http://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv_mini.jpg",
        "profileBannerURL": "https://pbs.twimg.com/profile_banners/76319318/1471443572/web",
        "biggerProfileImageURLHttps": "https://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv_bigger.jpg",
        "profileBackgroundImageURL": "http://pbs.twimg.com/profile_background_images/689475344793862144/n4dBUE1Q.jpg",
        "contributorsEnabled": false,
        "defaultProfileImage": false,
        "originalProfileImageURLHttps": "https://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv.jpg",
        "profileImageURLHttps": "https://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv_normal.jpg",
        "profileBannerIPadRetinaURL": "https://pbs.twimg.com/profile_banners/76319318/1471443572/ipad_retina",
        "followRequestSent": false,
        "profileBannerRetinaURL": "https://pbs.twimg.com/profile_banners/76319318/1471443572/web_retina",
        "miniProfileImageURLHttps": "https://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv_mini.jpg",
        "profileBannerMobileURL": "https://pbs.twimg.com/profile_banners/76319318/1471443572/mobile",
        "profileImageURL": "http://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv_normal.jpg",
        "profileBannerIPadURL": "https://pbs.twimg.com/profile_banners/76319318/1471443572/ipad",
        "profileBannerMobileRetinaURL": "https://pbs.twimg.com/profile_banners/76319318/1471443572/mobile_retina",
        "biggerProfileImageURL": "http://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv_bigger.jpg",
        "originalProfileImageURL": "http://pbs.twimg.com/profile_images/767693988811812864/iN01qTlv.jpg"
      },
      "withheldInCountries": null,
      "quotedStatus": null,
      "quotedStatusId": -1,
      "contributors": [],
      "retweet": false,
      "truncated": false,
      "retweeted": false,
      "urlentities": [],
      "favorited": false,
      "retweetedByMe": false,
      "possiblySensitive": false
    },
    "quotedStatusId": 806350858149593088,
    "contributors": [],
    "retweet": false,
    "truncated": false,
    "retweeted": false,
    "urlentities": [
      {
        "start": 28,
        "end": 51,
        "url": "https://t.co/haHuJnlCBC",
        "expandedURL": "https://twitter.com/bongolwinter/status/806350858149593088",
        "displayURL": "twitter.com/bongolwinter/s…",
        "text": "https://t.co/haHuJnlCBC"
      }
    ],
    "favorited": false,
    "retweetedByMe": false,
    "possiblySensitive": false
  },
  ...
```

## Get Any Tweet from List

```
GET {{baseUri}}api/twitter/anyTweetFromList?authzId=1&listOwner=hendybippo&listSlug=pk85
```

