/*
 * package com.dhatvibs.config;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.web.cors.CorsConfiguration; import
 * org.springframework.web.cors.UrlBasedCorsConfigurationSource; import
 * org.springframework.web.cors.CorsConfigurationSource;
 * 
 * import java.util.Arrays;
 * 
 * @Configuration public class CorsConfig {
 * 
 * @Bean public CorsConfigurationSource corsConfigurationSource() {
 * 
 * CorsConfiguration configuration = new CorsConfiguration();
 * 
 * // Allow all origins
 * configuration.setAllowedOriginPatterns(Arrays.asList(""));
 * 
 * configuration.setAllowedMethods(Arrays.asList( "GET", "POST", "PUT",
 * "DELETE", "OPTIONS" ));
 * 
 * configuration.setAllowedHeaders(Arrays.asList("*"));
 * 
 * configuration.setAllowCredentials(true);
 * 
 * UrlBasedCorsConfigurationSource source = new
 * UrlBasedCorsConfigurationSource();
 * 
 * source.registerCorsConfiguration("/**", configuration);
 * 
 * return source; } }
 */

package com.dhatvibs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                       // .allowedOrigins("http://localhost:3000")
                        //.allowedOrigins("http://localhost:3000","https://performance-dashboard-umber.vercel.app")
                        //.allowedOrigins("http://fieldconnect.dhatvibs.com","http://localhost:3000")
                		.allowedOrigins("https://performance-dashboard-fe.onrender.com","http://localhost:3000")
                       // .allowedOrigins("*")
                        .allowCredentials(true)
                        .allowedMethods("*");
            }
        };
    }
}
