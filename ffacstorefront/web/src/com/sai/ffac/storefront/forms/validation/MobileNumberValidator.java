/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2014 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.sai.ffac.storefront.forms.validation;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sai.ffac.storefront.forms.UpdateMobileNumberForm;


/**
 * Validator for sap code forms.
 */
@Component("mobileNumberValidator")
public class MobileNumberValidator implements Validator
{

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return UpdateMobileNumberForm.class.equals(aClass);
	}

	@Override
	public void validate(final Object object, final Errors errors)
	{
		final UpdateMobileNumberForm profileForm = (UpdateMobileNumberForm) object;
		final String mobileNumber = profileForm.getMobileNumber();

		final int MOBILE_NUM_LEN = 8; //should move this to properties file
		final String EIGHT_CHAR = "8";
		final String NINE_CHAR = "9";

		final boolean isValid = StringUtils.isNotEmpty(mobileNumber) && StringUtils.isNumeric(mobileNumber)
				&& StringUtils.startsWithAny(mobileNumber, new String[]
				{ EIGHT_CHAR, NINE_CHAR }) && (mobileNumber.length() <= MOBILE_NUM_LEN);

		if (!isValid)
		{
			errors.rejectValue("mobileNumber", "profile.mobileNumber.invalid");
		}
	}

}
