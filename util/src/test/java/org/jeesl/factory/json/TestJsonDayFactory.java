package org.jeesl.factory.json;

import java.util.List;

import org.jeesl.JeeslBootstrap;
import org.jeesl.factory.json.util.JsonDayFactory;
import org.jeesl.model.json.util.time.JsonDay;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJsonDayFactory
{
	final static Logger logger = LoggerFactory.getLogger(TestJsonDayFactory.class);

	private JsonDayFactory f;
	
	public TestJsonDayFactory()
	{
		f = new JsonDayFactory("en");
	}
	
	protected void cli()
	{
		DateTime dtFrom = new DateTime();
		DateTime dtTo = (new DateTime()).plusDays(5);
		
		List<JsonDay> days = f.build(dtFrom.toDate(),dtTo.toDate());
		logger.info("size: "+days.size());
		for(JsonDay d : days)
		{
			logger.info(d.getNr()+"."+d.getMonth()+"."+d.getYear());
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		JeeslBootstrap.init();
		TestJsonDayFactory cli = new TestJsonDayFactory();
		cli.cli();
	}
}