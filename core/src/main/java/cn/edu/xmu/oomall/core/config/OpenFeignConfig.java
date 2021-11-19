/**
 * Copyright School of Informatics Xiamen University
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package cn.edu.xmu.oomall.core.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 将包头原样复制的OpenFeign的请求包头中
 *
 * @author mingqiu
 */
@Configuration
public class OpenFeignConfig{

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new OpenFeignHeaderInterceptor();
    }
}

@Slf4j
class OpenFeignHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        if (request == null) {
            return;
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return;
        }
        log.info("feign interceptor.....");
        // 把请求过来的header请求头 原样设置到feign请求头中
        // 包括token
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            requestTemplate.header(name, request.getHeader(name));
        }
    }
}
