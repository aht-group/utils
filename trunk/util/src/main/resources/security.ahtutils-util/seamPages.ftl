package ${packageName};

import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

<#list views as v>
import ${v.import};
</#list>

@ViewConfig
public interface SeamPages
{
<#list views as v>

	static enum Pages${v_index}
    {
    	@ViewPattern("${v.viewPattern}")
        @UrlMapping(pattern="${v.urlMapping}")
        @LoginView("${loginView}")
        @AccessDeniedView("${accessDeniedView}")
        @RestrictAtPhase(PhaseIdType.RESTORE_VIEW)
        @${v.identifier}
        ${v.enum};
    }
</#list> 
}