package org.jeesl.controller.handler.module.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowProcesslResetHandler
{
	final static Logger logger = LoggerFactory.getLogger(WorkflowProcesslResetHandler.class);
	
	private boolean stages; public boolean isStages() {return stages;} public WorkflowProcesslResetHandler stages(boolean stages) {this.stages = stages;return this;}
	private boolean stage; public boolean isStage() {return stage;} public WorkflowProcesslResetHandler stage(boolean stage) {this.stage = stage;return this;}

	private boolean documents; public boolean isDocuments() { return documents;} public WorkflowProcesslResetHandler documents(boolean documents) {this.documents = documents;return this;}
	private boolean document; public boolean isDocument() {return document;} public WorkflowProcesslResetHandler document(boolean document) {this.document = document; return this;}
	
	private boolean permissions; public boolean isPermissions() {return permissions;} public WorkflowProcesslResetHandler permissions(boolean permissions) {this.permissions = permissions;return this;}
	private boolean permission; public boolean isPermission() {return permission;} public WorkflowProcesslResetHandler permission(boolean permission) {this.permission = permission;return this;}
	
	private boolean notifications; public boolean isNotifications() {return notifications;} public WorkflowProcesslResetHandler notifications(boolean notifications) {this.notifications = notifications;return this;}
	private boolean notification; public boolean isNotification() {return notification;} public WorkflowProcesslResetHandler notification(boolean notification) {this.notification = notification;return this;}
	
	private boolean transistions; public boolean isTransistions() {return transistions;} public WorkflowProcesslResetHandler transistions(boolean transistions) {this.transistions = transistions; return this;}
	private boolean transistion; public boolean isTransistion() {return transistion;} public WorkflowProcesslResetHandler transistion(boolean transistion) {this.transistion = transistion; return this;}
	
	private boolean communications; public boolean isCommunications() {return communications;} public WorkflowProcesslResetHandler communications(boolean communications) {this.communications = communications;return this;}
	private boolean communication; public boolean isCommunication() {return communication;} public WorkflowProcesslResetHandler communication(boolean communication) {this.communication = communication;return this;}
	
	private boolean actions; public boolean isActions() {return actions;} public WorkflowProcesslResetHandler actions(boolean actions) {this.actions = actions;return this;}
	private boolean action; public boolean isAction() {return action;} public WorkflowProcesslResetHandler action(boolean action) {this.action = action; return this;}
	
	private WorkflowProcesslResetHandler()
	{
		
	}
	
	public static WorkflowProcesslResetHandler build() {return new WorkflowProcesslResetHandler();}
	
	public WorkflowProcesslResetHandler all()
	{
		documents = true;
		document = true;
		stages = true;
		stage = true;
		permissions = true;
		permission = true;
		notifications = true;
		notification = true;
		transistions = true;
		transistion = true;
		communications = true;
		communication = true;
		actions = true;
		action = true;
		return this;
	}
	
	public WorkflowProcesslResetHandler none()
	{
		documents = false;
		document = false;
		stages = false;
		stage = false;
		permissions = false;
		permission = false;
		notifications = false;
		notification = false;
		transistions = false;
		transistion = false;
		communications = false;
		communication = false;
		actions = false;
		action = false;
		return this;
	}
}