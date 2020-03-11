package org.jeesl.factory.json.system.io.ssi.docker;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.model.json.system.io.ssi.docker.JsonDockerContainer;
import org.jeesl.model.json.system.io.ssi.docker.JsonDocker;

public class JsonDockerFactory
{
	public static JsonDocker build(List<JsonDockerContainer> list)
	{
		JsonDocker json = new JsonDocker();
		json.setContainers(new ArrayList<JsonDockerContainer>());
		json.getContainers().addAll(list);
		return json;
	}
}