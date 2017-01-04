package demo;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Main application startup class.
 * 
 * @author D. Ashmore
 *
 */
public class AdminUIApplication extends Application<AdminUIConfiguration> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminUIApplication.class);

	@Override
	public void run(AdminUIConfiguration config, Environment environment) throws Exception {
		LOGGER.info("Hi There! I'm on my way, I'm making it.");
	}
	
	@Override
    public void initialize(Bootstrap<AdminUIConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
		bootstrap.addBundle(new AssetsBundle("/assets", "/pages"));
		bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
		bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
		bootstrap.addBundle(new AssetsBundle("/assets/json", "/json", null, "json"));

		// We shouldn't have to do this: Zuul forwarding doesn't like the first
		// json exposure for some reason
		bootstrap.addBundle(new AssetsBundle("/assets/json", "/pages/json", null, "json2"));

    }

	public static void main(String[] args) throws Exception {
		String[] localArgs = args;
		if (ArrayUtils.isEmpty(localArgs)) {
			localArgs = (String[])ArrayUtils.add(localArgs, "server");
			localArgs = (String[])ArrayUtils.add(localArgs, "admin-ui.yml");
		}
		new AdminUIApplication().run(localArgs );
		
	}

}
