package org.jeesl.model.json.survey;

import java.io.Serializable;

import org.jeesl.interfaces.model.survey.JeeslSimpleSurveyQuestion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value = "question")
public class Question implements Serializable,JeeslSimpleSurveyQuestion
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@JsonProperty("position")
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	@JsonProperty("visible")
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
	@JsonProperty("code")
	private String code;
	@Override public String getCode(){return code;}
	@Override public void setCode(String code){this.code = code;}
	@JsonIgnore public boolean isSetCode() {return code!=null;}
	
	@JsonProperty("topic")
	private String topic;
	@Override public String getTopic(){return topic;}
	@Override public void setTopic(String topic){this.topic = topic;}
	@JsonIgnore public boolean isSetTopic() {return topic!=null;}
	
	@JsonProperty("question")
	private String question;
	public String getQuestion() {return question;}
	public void setQuestion(String question) {this.question = question;}
	@JsonIgnore public boolean isSetQuestion() {return question!=null;}
	
	@JsonProperty("remark")
	private String remark;
	@Override public String getRemark() {return remark;}
	@Override public void setRemark(String remark){this.remark=remark;}
	@JsonIgnore public boolean isSetRemark() {return remark!=null;}
	
	@JsonProperty("calculateScore")
	private Boolean calculateScore;
	public Boolean getCalculateScore() {return calculateScore;}
	public void setCalculateScore(Boolean calculateScore) {this.calculateScore = calculateScore;}
	@JsonIgnore public boolean isSetCalculateScore() {return calculateScore!=null;}
	
	@JsonProperty("minScore")
	private Double minScore;
	public Double getMinScore() {return minScore;}
	public void setMinScore(Double minScore) {this.minScore = minScore;}
	@JsonIgnore public boolean isSetMinScore() {return minScore!=null;}
	
	@JsonProperty("maxScore")
	private Double maxScore;
	public Double getMaxScore() {return maxScore;}
	public void setMaxScore(Double maxScore) {this.maxScore = maxScore;}
	@JsonIgnore public boolean isSetMaxScore() {return maxScore!=null;}
	
	@JsonProperty("showBoolean")
	private Boolean showBoolean;
	@Override public Boolean getShowBoolean() {return showBoolean;}
	@Override public void setShowBoolean(Boolean showBoolean) {this.showBoolean=showBoolean;}
	@JsonIgnore public boolean isSetShowBoolean() {return showBoolean!=null;}
	
	@JsonProperty("showInteger")
	private Boolean showInteger;
	@Override public Boolean getShowInteger() {return showInteger;}
	@Override public void setShowInteger(Boolean showInteger) {this.showInteger=showInteger;}
	@JsonIgnore public boolean isSetShowInteger() {return showInteger!=null;}
	
	@JsonProperty("showDouble")
	private Boolean showDouble;
	@Override public Boolean getShowDouble() {return showDouble;}
	@Override public void setShowDouble(Boolean showDouble) {this.showDouble=showDouble;}
	@JsonIgnore public boolean setShowDouble() {return showDouble!=null;}
	
	@JsonProperty("showText")
	private Boolean showText;
	@Override public Boolean getShowText() {return showText;}
	@Override public void setShowText(Boolean showText) {this.showText=showText;}
	@JsonIgnore public boolean isSetShowText() {return showText!=null;}
	
	@JsonProperty("showScore")
	private Boolean showScore;
	@Override public Boolean getShowScore() {return showScore;}
	@Override public void setShowScore(Boolean showScore) {this.showScore=showScore;}
	@JsonIgnore public boolean isSetShowScore() {return showScore!=null;}
	
	@JsonProperty("showRemark")
	private Boolean showRemark;
	@Override public Boolean getShowRemark() {return showRemark;}
	@Override public void setShowRemark(Boolean showRemark) {this.showRemark=showRemark;}
	@JsonIgnore public boolean isSetShowRemark() {return showRemark!=null;}
	
	@JsonProperty("showSelectOne")
	private Boolean showSelectOne;
	@Override public Boolean getShowSelectOne() {return showSelectOne;}
	@Override public void setShowSelectOne(Boolean showSelectOne) {this.showSelectOne = showSelectOne;}
	@JsonIgnore public boolean isSetShowSelectOne() {return showSelectOne!=null;}
	
	@JsonProperty("showSelectMulti")
	private Boolean showSelectMulti;
	@Override public Boolean getShowSelectMulti() {return showSelectMulti;}
	@Override public void setShowSelectMulti(Boolean showSelectMulti) {this.showSelectMulti = showSelectMulti;}
	@JsonIgnore public boolean isSetShowSelectMulti() {return showSelectMulti!=null;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}