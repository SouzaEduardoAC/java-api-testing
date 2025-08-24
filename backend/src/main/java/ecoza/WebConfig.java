package ecoza;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Applies CORS policy to all endpoints under /api
                .allowedOrigins("*") // Allows all origins. For production, you should restrict this to your frontend's domain.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specifies allowed HTTP methods.
                .allowedHeaders("*") // Allows all headers in the request.
                .allowCredentials(false); // `true` is not allowed when `allowedOrigins` is `*`.
    }
}