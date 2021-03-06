package com.filebinding.core.config;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.filebinding.core.exception.ConfigException;
import com.filebinding.core.exception.FieldException;
import com.filebinding.core.exception.MappingException;
import com.filebinding.core.fieldSupport.FieldParser;
import com.filebinding.core.fieldSupport.FieldRenderer;
import com.filebinding.core.util.introspector.IntrospectUtil;

public class DocumentFieldConfiguration implements Serializable {

	private static final long serialVersionUID = 4628855917108563200L;

	private final static Log log = LogFactory.getLog(DocumentFieldConfiguration.class);

	/**Configuration field**/
	private String name;

	private String columnName;

	private String groupName;

	private boolean required;

	private String parseFormatStr;

	private String buildFormatStr;

	private String flag;

	/**********UnConfiguration field*****************/	
	private List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

	private Class type;

	private int propertyDesSize=-1;

	private boolean valid = true;

	private FieldRenderer fieldRenderer;

	private FieldParser fieldParser;

	private static final String EMPTY_STR = "";

	private final static DocumentFieldConfiguration ignoreField = new DocumentFieldConfiguration(false);
	public static DocumentFieldConfiguration getIgnoreFieldConfiguration(){
		return ignoreField;
	}

	/***Set Default Value of Field Config here***/
	public DocumentFieldConfiguration(){
	}

	private DocumentFieldConfiguration(boolean valid){
		this.valid = valid;
	}

	public String getName() {
		return name;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getGroupName() {
		return groupName;
	}

	public boolean isRequired() {
		return required;
	}

	public String getBuildFormatStr() {
		return buildFormatStr;
	}

	public String getParseFormatStr() {
		return parseFormatStr;
	}

	public String getFlag() {
		return flag;
	}

	public boolean isValid() {
		return valid;
	}

	public FieldParser getFieldParser() {
		return fieldParser;
	}

	public FieldRenderer getFieldRenderer() {
		return fieldRenderer;
	}

	public Class getType(){
		return type;
	}

	public int getPropertyDesSize(){
		if(propertyDesSize == -1)
			propertyDesSize = propertyDescriptors.size();

		return propertyDesSize;
	}

	/***protected for setter***/
	protected void setName(String name) {
		this.name = name;
	}

	protected void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	protected void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	protected void setRequired(boolean required) {
		this.required = required;
	}

	protected void setFormatStr(String formatStr) {
		if(parseFormatStr == null || parseFormatStr.trim().length() == 0)
			parseFormatStr = formatStr;

		if(buildFormatStr == null || buildFormatStr.trim().length() == 0)
			buildFormatStr = formatStr;
	}

	protected void setFlag(String flag) {
		this.flag = flag;
	}

	protected void setValid(boolean valid) {
		this.valid = valid;
	}

	protected void setParserName(String parserName) throws ConfigException {
		buildParser(parserName);
	}

	protected void setFieldParser(FieldParser fieldParser) {
		this.fieldParser = fieldParser;
	}

	protected void setRendererName(String rendererName) throws ConfigException {
		buildRenderer(rendererName);
	}

	protected void setFieldRenderer(FieldRenderer fieldRenderer) {
		this.fieldRenderer = fieldRenderer;
	}

	protected void setRendererInstance(FieldRenderer fieldRenderer){
		this.fieldRenderer = fieldRenderer;
	}

	protected void setBuildFormatStr(String buildFormatStr) {
		this.buildFormatStr = buildFormatStr;
	}

	protected void setParseFormatStr(String parseFormatStr) {
		this.parseFormatStr = parseFormatStr;
	}

	protected void setPropertyDescriptors(List<PropertyDescriptor> propertyDescriptors) {
		this.propertyDescriptors = propertyDescriptors;

		if(getPropertyDesSize() > 0){
			this.type = propertyDescriptors.get(getPropertyDesSize() -1).getPropertyType();
		}
	}

	private void buildParser(String parserName) throws ConfigException {
		final String info = "\nbuildRenderer - ";

		if(parserName == null || parserName.trim().length() == 0)
			return;

		try{
			fieldParser = (FieldParser) Class.forName(parserName).newInstance();
		}catch ( ClassNotFoundException e ){
			log.error(info + "ClassNotFoundException for csv parser: " + parserName, e);
			throw new ConfigException(info + "ClassNotFoundException for csv parser: " + parserName,e);
		}catch ( InstantiationException e ){
			log.error(info + "InstantiationException for csv parser: " + parserName, e);
			throw new ConfigException(info + "InstantiationException for csv parser: " + parserName,e);
		}catch ( IllegalAccessException e ){
			log.error(info + "IllegalAccessException for csv parser: " + parserName, e);
			throw new ConfigException(info + "IllegalAccessException for csv parser: " + parserName,e);
		}catch ( Exception e ){
			log.error(info + "Unknown Exception for csv parser: " + parserName, e);
			throw new ConfigException(info + "Unknown Exception for csv parser: " + parserName,e);
		}
	}

	private void buildRenderer(String rendererName) throws ConfigException {
		final String info = "\nbuildRenderer - ";

		if(rendererName == null || rendererName.trim().length() == 0)
			return;

		try{
			fieldRenderer = (FieldRenderer) Class.forName(rendererName).newInstance();
		}catch ( ClassNotFoundException e ){
			log.error(info + "ClassNotFoundException for csv renderer: " + rendererName, e);
			throw new ConfigException(info + "ClassNotFoundException for csv renderer: " + rendererName,e);
		}catch ( InstantiationException e ){
			log.error(info + "InstantiationException for csv renderer: " + rendererName, e); 
			throw new ConfigException(info + "InstantiationException for csv renderer: " + rendererName,e);
		}catch ( IllegalAccessException e ){
			log.error(info + "IllegalAccessException for csv renderer: " + rendererName, e); 
			throw new ConfigException(info + "IllegalAccessException for csv renderer: " + rendererName,e);
		}catch ( Exception e ){
			log.error(info + "Unknown Exception for csv renderer: " + rendererName, e); 
			throw new ConfigException(info + "Unknown Exception for csv renderer: " + rendererName,e);
		}
	}

	//Copy could be public
	public DocumentFieldConfiguration copy() throws ConfigException {
		DocumentFieldConfiguration fieldConfiguration = new DocumentFieldConfiguration();

		fieldConfiguration.setName(name);
		fieldConfiguration.setColumnName(columnName);
		fieldConfiguration.setGroupName(groupName);
		fieldConfiguration.setRequired(required);
		fieldConfiguration.setParseFormatStr(parseFormatStr);
		fieldConfiguration.setBuildFormatStr(buildFormatStr);
		fieldConfiguration.setFlag(flag);
		fieldConfiguration.setPropertyDescriptors(propertyDescriptors);
		fieldConfiguration.setFieldParser(fieldParser);
		fieldConfiguration.setFieldRenderer(fieldRenderer);

		return fieldConfiguration;
	}

	/***Used for business layer***/
	public void setFieldValue(String fieldValue, Object bean) throws MappingException{
		String info = "\n DocumentFieldConfiguration::setFieldValue - ";

		if(!valid)
			return;

		if((fieldValue == null || fieldValue.trim().length() == 0) && required){
			String errMsg = "Record Field: [" + name + "] is not defined in imported File";
			log.error(info + errMsg);
			throw new FieldException(name, errMsg);
		}

		if(fieldValue != null)//FIXME, shall we trim value here, or let unTrimed value go to parser
			fieldValue = fieldValue.trim();

		/***Convert Data***/
		Object covertedValue = null;

		try{
			covertedValue = fieldParser.getParsedValue(fieldValue, bean, this);
		}catch(Exception e){
			log.error(info + e.getMessage(), e);
			throw new FieldException(name,fieldValue, e.getMessage(), e);
		}

		/***Set Value to mapping field***/
		try{
			IntrospectUtil.setPropertyValue(propertyDescriptors, bean, covertedValue);
		}catch(Exception e){
			log.error(info + e.getMessage(), e);
			throw new FieldException(name,fieldValue, e.getMessage(), e);
		}

		if(log.isTraceEnabled())
			log.trace(info + "set field name: [" + name + "] with value: [" + fieldValue + "]");
	}
	
	
	public Object getFieldObjectValue(Object bean)throws MappingException{
		
		String info = "\n DocumentFieldConfiguration::getFieldValue - ";

		if(!valid)
			return EMPTY_STR;

		Object pojoValue = null;

		try{
			pojoValue = IntrospectUtil.getPropertyValue(propertyDescriptors, bean);
		}catch(Exception e){
			log.error(info + e.getMessage(), e);
			throw new FieldException(name, e.getMessage(), e);
		}

		Object fieldValue = null;

		try{
			fieldValue = fieldRenderer.getRenderedObjectValue(pojoValue, bean, this);
		}catch(Exception e){
			log.error(info + e.getMessage(), e);
			throw new FieldException(name, fieldValue.toString(),e.getMessage(), e);
		}

		if(fieldValue == null || ( fieldValue instanceof String &&  (  (String)(fieldValue)).trim().length() == 0 ) ){
			if(required){
				String errMsg = "Record Field: [" + name + "] is missing or not valid";
				log.error(info + errMsg);
				throw new FieldException(name, errMsg);
			}else{
				return EMPTY_STR;
			}
		}

		return fieldValue;
		
	}

	public String getFieldValue(Object bean)throws MappingException{
		String info = "\n DocumentFieldConfiguration::getFieldValue - ";

		if(!valid)
			return EMPTY_STR;

		Object pojoValue = null;

		try{
			pojoValue = IntrospectUtil.getPropertyValue(propertyDescriptors, bean);
		}catch(Exception e){
			log.error(info + e.getMessage(), e);
			throw new FieldException(name, e.getMessage(), e);
		}

		String fieldValue = null;

		try{
			fieldValue = fieldRenderer.getRenderedValue(pojoValue, bean, this);
		}catch(Exception e){
			log.error(info + e.getMessage(), e);
			throw new FieldException(name,fieldValue, e.getMessage(), e);
		}

		if(fieldValue == null || fieldValue.trim().length() == 0){
			if(required){
				String errMsg = "Record Field: [" + name + "] is missing or not valid";
				log.error(info + errMsg);
				throw new FieldException(name, errMsg);
			}else{
				return EMPTY_STR;
			}
		}

		return fieldValue;
	}
}
