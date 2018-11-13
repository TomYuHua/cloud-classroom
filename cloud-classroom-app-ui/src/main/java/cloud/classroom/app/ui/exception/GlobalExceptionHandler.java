package cloud.classroom.app.ui.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cloud.classroom.app.ui.dto.ErrorInfo;

@ControllerAdvice
public class GlobalExceptionHandler
{
	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception
	{
		ModelAndView modeView = new ModelAndView();
		modeView.addObject("exception", e);
		modeView.addObject("url", req.getRequestURL());
		modeView.setViewName(DEFAULT_ERROR_VIEW);
		return modeView;
	}

	@ExceptionHandler(value = RestFullException.class)
	@ResponseBody
	public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, RestFullException e) throws Exception
	{
		ErrorInfo<String> error = new ErrorInfo<>();
		error.setMessage(e.getMessage());
		error.setCode(ErrorInfo.ERROR);
		error.setData("Some Data");
		error.setUrl(req.getRequestURL().toString());
		return error;
	}

}
