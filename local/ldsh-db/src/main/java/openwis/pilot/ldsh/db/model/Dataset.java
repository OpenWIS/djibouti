package openwis.pilot.ldsh.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Dataset")
public class Dataset implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	 *                          "ids_gen")
	 * @SequenceGenerator(name = "ids_gen", sequenceName = "ids_sequence",
	 *                         allocationSize = 20) -- >>
	 *                         org.hibernate.dialect.MySQL5Dialect does not
	 *                         support sequences
	 */

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "periodfrom")
	private Date periodFrom;

	@Column(name = "periodto")
	private Date periodTo;

	@Column(name = "license")
	private String license;

	@Column(name = "imageurl")
	private String imageUrl;

	@Column(name = "measurementunit")
	private String measurementUnit; // todo Enum

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Dataset_WmoCode", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "wmocode_id") })
	private Set<WmoCode> WmoCodes = new HashSet<>();

	// Location Information
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "elevation")
	private String elevation;

	// Dissemination Information
	@Column(name = "relativeurl")
	private String relativeUrl;

	@Column(name = "filenameprefix")
	private String filenameprefix;

	@Column(name = "downloadUrl")
	private String downloadUrl;

	@Column(name = "subscriptionuri")
	private String subscriptionUri;

	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "dataformat_id")
	private DataFormat dataformat;

	@Column(name = "rdshdissenabled")
	private boolean rdshDissEnabled; // null true false? Notification Only
										// Notification + Data

	// Search Engine Metadata
	@Column(name = "jsonld")
	private String jsonLd;

	public Set<WmoCode> getWmoCodes() {
		return WmoCodes;
	}

	public void setWmoCodes(Set<WmoCode> wmoCodes) {
		WmoCodes = wmoCodes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getElevation() {
		return elevation;
	}

	public void setElevation(String elevation) {
		this.elevation = elevation;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public String getFilenameprefix() {
		return filenameprefix;
	}

	public void setFilenameprefix(String filenameprefix) {
		this.filenameprefix = filenameprefix;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getSubscriptionUri() {
		return subscriptionUri;
	}

	public void setSubscriptionUri(String subscriptionUri) {
		this.subscriptionUri = subscriptionUri;
	}

	public DataFormat getDataformat() {
		return dataformat;
	}

	public void setDataformat(DataFormat dataformat) {
		this.dataformat = dataformat;
	}

	public boolean isRdshDissEnabled() {
		return rdshDissEnabled;
	}

	public void setRdshDissEnabled(boolean rdshDissEnabled) {
		this.rdshDissEnabled = rdshDissEnabled;
	}

	public String getJsonLd() {
		return jsonLd;
	}

	public void setJsonLd(String jsonLd) {
		this.jsonLd = jsonLd;
	}

}
