package org.cky.spider.exceptions;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// not used
public class GlobalExceptionHandler {
	
private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleIOException( IllegalArgumentException ex){
		System.out.println("IllegalArgumentException");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", ex.getMessage());
		mav.setViewName("errors");
		
		return mav;
	}
	@ExceptionHandler(MalformedURLException.class)
	public ModelAndView handleURLException(MalformedURLException ex){
		System.out.println("MalformedURLException");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", ex.getMessage());
		mav.setViewName("errors");
		
		return mav;
	}
	@ExceptionHandler(Exception.class)
	public ModelAndView handleIOException(Exception ex){
		
		System.out.println("Exception");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", ex.getMessage());
		mav.setViewName("errors");
		
		return mav;
	}
	
}
