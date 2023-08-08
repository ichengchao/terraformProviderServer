package terraformDemo.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import terraformDemo.common.CommonConstants;
import terraformDemo.model.WebResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(SystemController.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		if (CommonConstants.LOAD_ON_START_UP) {
			CommonConstants.loadProperties();
		}
	}

	/**
	 * 打印Http请求的所有内容
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/logrequest.do")
	public void logrequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebResult result = new WebResult();
		try {
			Enumeration<String> headerNames = request.getHeaderNames();
			System.out.println("=====================Http Request===============");
			System.out.println(request.getRequestURL());
			System.out.println("Method: " + request.getMethod());
			System.out.println("=====================Head=======================");
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				System.out.println("[Header] " + headerName + " : " + request.getHeader(headerName));
			}
			System.out.println("====================QueryString=================");
			System.out.println(request.getQueryString());
			System.out.println("====================Body========================");
			String characterEncoding = request.getCharacterEncoding();
			Charset charset = Charset.forName(characterEncoding);
			System.out.println(IOUtils.toString(request.getInputStream(), charset));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setErrorMsg(e.getMessage());
		}
		outputToJSON(response, result);
	}

	@RequestMapping("/getIndexLogoPage.do")
	public void getIndexLogoPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebResult result = new WebResult();
		try {
			String username = "welcome";
			String logoDiv = null;
			String version = "terraformDemo";
			logoDiv = "<div align=\"center\"><i style=\"font-size:30px;margin-top:5px;color:#CFDEEF;animation-duration: 1s;\" class=\"fa fa-sun-o fa-spin\" aria-hidden=\"true\"></i></div><div align='center' style='background-color:rgb(93,168,48);margin-top:5px;font-size: 12px;"
					+ "'><font style='color: white;'>" + username + "<br>" + version + "</font></div>";
			result.setData(logoDiv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setErrorMsg(e.getMessage());
		}
		outputToJSON(response, result);
	}

}
