package org.jeesl.controller.handler;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ResourceWrapper;

public class AbstractBuildVersionResourceHandler extends ResourceHandlerWrapper
{
	private ResourceHandler wrapped;
	private String cssTimeStamp;
	public String getCssTimeStamp() {return cssTimeStamp;}
	public void setCssTimeStamp(String cssTimeStamp) {this.cssTimeStamp = cssTimeStamp;}

	public AbstractBuildVersionResourceHandler(ResourceHandler wrapped)
	{
        this.wrapped = wrapped;
    }

	@Override
	public Resource createResource(String resourceName)
	{
		return createResource(resourceName, null, null);
	}

	@Override
	public Resource createResource(String resourceName, String libraryName)
	{
		return createResource(resourceName, libraryName, null);
	}

	@Override
	public Resource createResource(String resourceName, String libraryName, String contentType)
	{
		final Resource resource = super.createResource(resourceName, libraryName, contentType);

		if (resource == null)
		{
			return null;
		}

		return new ResourceWrapper() {

			@Override
			public String getRequestPath()
			{
				return super.getRequestPath() + "&buildVersion=" + cssTimeStamp;
			}

			@Override
			public String getResourceName()
			{
				return resource.getResourceName();
			}

			@Override
			public String getLibraryName()
			{
				return resource.getLibraryName();
			}

			@Override
			public String getContentType()
			{
				return resource.getContentType();
			}

			@Override
			public Resource getWrapped()
			{
				return resource;
			}
		};
	}

	@Override
	public ResourceHandler getWrapped()
	{
		return wrapped;
	}
}
