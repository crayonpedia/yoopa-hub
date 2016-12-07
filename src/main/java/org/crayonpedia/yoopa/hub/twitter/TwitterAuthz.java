package org.crayonpedia.yoopa.hub.twitter;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Authorization by a specific Twitter user granted to a specific Twitter Consumer App.
 * This can be stored as embedded data structure in file or database for tenant, person, shop, etc.
 */
@Entity
//@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, property="@type")
//@JsonSubTypes(@JsonSubTypes.Type(name="TwitterAuthorization", value=TwitterAuthorization.class))
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterAuthz implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Long userId;
	@ManyToOne(optional = false) @JoinColumn(name = "twitterApp_id") @JsonIgnore
	private TwitterApp twitterApp;
	private String screenName;
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentOffsetDateTime")
	private OffsetDateTime creationTime;
	private String accessToken;
	private String accessTokenSecret;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TwitterApp getTwitterApp() {
		return twitterApp;
	}

	public void setTwitterApp(TwitterApp twitterApp) {
		this.twitterApp = twitterApp;
	}

	/**
	 * Twitter User's ID, if known.
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Twitter User's screen name, if known.
	 * @return
	 */
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * When this authorization was granted.
	 * @return
	 */
	public OffsetDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(OffsetDateTime creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * Returns the value of the '<em><b>Twitter Tenant Access Token</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Access token that has access to this tenant's Twitter account, referred by getTwitterTenantScreenName().
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Twitter Tenant Access Token</em>' attribute.
	 * @see #setAccessToken(String)
	 * @model
	 * @generated
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the value of the '{@link TwitterAuthz#getAccessToken <em>Twitter Tenant Access Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Twitter Tenant Access Token</em>' attribute.
	 * @see #getAccessToken()
	 * @generated
	 */
	public void setAccessToken(String value) {
		this.accessToken = value;
	}

	/**
	 * Returns the value of the '<em><b>Twitter Tenant Access Token Secret</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Access token secret that has access to this tenant's Twitter account, referred by #getTwitterTenantScreenName().
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Twitter Tenant Access Token Secret</em>' attribute.
	 * @see #setAccessTokenSecret(String)
	 * @model
	 * @generated
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * Sets the value of the '{@link TwitterAuthz#getAccessTokenSecret <em>Twitter Tenant Access Token Secret</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Twitter Tenant Access Token Secret</em>' attribute.
	 * @see #getAccessTokenSecret()
	 * @generated
	 */
	public void setAccessTokenSecret(String value) {
		this.accessTokenSecret = value;
	}
} // TwitterSysConfig
