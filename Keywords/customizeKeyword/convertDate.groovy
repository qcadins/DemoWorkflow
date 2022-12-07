package customizeKeyword

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.DateFormat
import java.text.SimpleDateFormat

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class convertDate {
	@Keyword
	public convertDateSdf (String date){

		'convert date confins dan excel agar sama'
		SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy')

		Date parsedDate = null

		String sentDate = date

		String sDate

		if (sentDate != '') {
			parsedDate = sdf.parse(sentDate)

			sdf = new SimpleDateFormat('dd MMM YYYY')

			sDate = sdf.format(parsedDate)
		}

		return sDate
	}

	@Keyword
	public countDateInsurance (String enddate){
		'untuk mengubah format tanggal'
		Date enddate_Formated = new SimpleDateFormat('MM/dd/yyyy').parse(enddate)

		String inslength = GlobalVariable.InsuranceLength

		Calendar cal = Calendar.getInstance()

		cal.setTime(enddate_Formated)

		cal.add(Calendar.MONTH, Integer.parseInt(inslength))

		DateFormat dateFormat = new SimpleDateFormat('MM/dd/yyyy')

		String enddateFinal = dateFormat.format(cal.getTime())

		return enddateFinal
	}
}
