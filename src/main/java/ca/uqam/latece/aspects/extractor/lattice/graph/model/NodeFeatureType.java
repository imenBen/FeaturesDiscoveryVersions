package ca.uqam.latece.aspects.extractor.lattice.graph.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeFeatureType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1139024176362154163L;
	@JsonProperty("FeatureTypeName")
	private String FeatureTypeName;
	@JsonProperty("anchor")	
	private String anchor;
	@JsonProperty("coverage")	
	private float coverage;
	
	
	
	public NodeFeatureType() {
		super();
	}
	public String getFeatureTypeName() {
		return FeatureTypeName;
	}
	public void setFeatureTypeName(String featureTypeName) {
		FeatureTypeName = featureTypeName;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public float getCoverage() {
		return coverage;
	}
	public void setCoverage(float coverage) {
		this.coverage = coverage;
	}
	@Override
	public String toString() {
		return "NodeFeatureType [FeatureTypeName=" + FeatureTypeName + ", anchor=" + anchor + ", coverage=" + coverage
				+ "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(FeatureTypeName, anchor);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeFeatureType other = (NodeFeatureType) obj;
		return Objects.equals(FeatureTypeName, other.FeatureTypeName) && Objects.equals(anchor, other.anchor);
	}
	
	
}
