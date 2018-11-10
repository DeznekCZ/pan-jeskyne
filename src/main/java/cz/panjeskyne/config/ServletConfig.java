package cz.panjeskyne.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"cz.panjeskyne"})
public class ServletConfig implements WebMvcConfigurer {
	
	@Value("${development.enabled:false}")
	private boolean development;
	
	@Bean
	public ITemplateResolver templateResolver(ServletContext servletContext) {
		ServletContextTemplateResolver viewResolver = new ServletContextTemplateResolver(servletContext);
		viewResolver.setTemplateMode(TemplateMode.HTML);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".html");
		viewResolver.setCacheable(!development);
		viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		return viewResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new LayoutDialect());
		return templateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine);
		resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		resolver.setContentType("text/html; charset=UTF-8");
		return resolver;
	}
	
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
