package org.crayonpedia.yoopa.hub.twitter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Twitter Consumer App information, this does not contain any access token.
 * @see TwitterAuthz
 */
@Entity
//@JsonTypeInfo(use=com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, property="@type")
//@JsonSubTypes(@com.fasterxml.jackson.annotation.JsonSubTypes.Type(name="TwitterApp", value=TwitterApp.class))
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterApp {

	@Id
	private String id;
	private String name;
	private String apiKey;
	private String apiSecret;
	@OneToMany(mappedBy = "twitterApp")
	private Set<TwitterAuthz> authzs = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TwitterAuthz> getAuthzs() {
		return authzs;
	}

	public void setAuthzs(Set<TwitterAuthz> authzs) {
		this.authzs = authzs;
	}

	/**
	 * Returns the value of the '<em><b>Twitter Consumer Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Twitter consumer key for this tenant.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Twitter Consumer Key</em>' attribute.
	 * @see #setApiKey(String)
	 * @model
	 * @generated
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Sets the value of the '{@link TwitterApp#getApiKey <em>Twitter Consumer Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Twitter Consumer Key</em>' attribute.
	 * @see #getApiKey()
	 * @generated
	 */
	public void setApiKey(String value) {
		this.apiKey = value;
	}

	/**
	 * Returns the value of the '<em><b>Twitter Consumer Secret</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Twitter consumer key for this tenant.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Twitter Consumer Secret</em>' attribute.
	 * @see #setApiSecret(String)
	 * @model
	 * @generated
	 */
	public String getApiSecret() {
		return apiSecret;
	}

	/**
	 * Sets the value of the '{@link TwitterApp#getApiSecret <em>Twitter Consumer Secret</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Twitter Consumer Secret</em>' attribute.
	 * @see #getApiSecret()
	 * @generated
	 */
	public void setApiSecret(String value) {
		this.apiSecret = value;
	}

//	@Component
//	@RepositoryEventHandler(TwitterApp.class)
//	public static class TwitterAppEventHandler {
//		@Autowired
//		private HttpServletRequest req;
//
//		@HandleBeforeCreate
//		public void handleBeforeCreate(TwitterApp twitterApp) {
//			if ("PUT".equals(req.getMethod())) {
//				final String uri = req.getRequestURI();
//				final String id = StringUtils.substringAfterLast(uri, "/");
//				twitterApp.setId();
//			}
//		}
//	}


} // TwitterSysConfig
