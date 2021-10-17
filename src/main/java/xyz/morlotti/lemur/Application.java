package xyz.morlotti.lemur;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.extension.AbstractExtension;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mitchellbosecke.pebble.template.EvaluationContext;

@EnableFeignClients
@SpringBootApplication
public class Application
{
	/*----------------------------------------------------------------------------------------------------------------*/

	private static class FileExtFilter implements Filter
	{
		@Override
		public List<String> getArgumentNames()
		{
			return null;
		}

		@Override
		public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber)
		{
			if(input instanceof String)
			{
				String fname = (String) input;

				int index = fname.lastIndexOf(".");

				if(index > 0) return fname.substring(index).toLowerCase();
			}

			return "";
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	private static class FileSizeFilter implements Filter
	{
		private final String[] SCALES = new String[] {
			"Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB",
		};

		@Override
		public List<String> getArgumentNames()
		{
			return null;
		}

		@Override
		public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber)
		{
			/*--------------------------------------------------------------------------------------------------------*/

			double size;

			if(input != null)
			{
				/**/ if(input instanceof Long) {
					size = ((Long) input).doubleValue();
				}
				else if(input instanceof Integer) {
					size = ((Integer) input).doubleValue();
				}
				else if(input instanceof Float) {
					size = ((Float) input).doubleValue();
				}
				else if(input instanceof Double) {
					size = ((Double) input).doubleValue();
				}
				else {
					size = 0.0;
				}
			}
			else {
				size = 0.0;
			}

			/*--------------------------------------------------------------------------------------------------------*/

			if(size == 0.0)
			{
				return "0 Byte";
			}

			/* Compute the scale in base 1024 */

			int scale = (int) Math.floor(Math.log(size) / Math.log(1024.0));

			return String.format("%.1f %s", size / Math.pow(1024.0, scale), SCALES[scale]);

			/*--------------------------------------------------------------------------------------------------------*/
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	@SuppressWarnings("used")
	public Extension myPebbleExtension()
	{
		return new AbstractExtension() {

			private final Map<String, Filter> filters = new HashMap<>();

			/* anonymous constructor */
			{
				filters.put("fileExt", new FileExtFilter());
				filters.put("fileSize", new FileSizeFilter());
			}

			@Override
			public Map<String, Filter> getFilters()
			{
				return filters;
			}
		};
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
