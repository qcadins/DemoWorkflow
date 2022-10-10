package sortPaging

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

public class verifySortPaging {
	// empty string: guaranteed to be less than or equal to any other
	@Keyword
	public verifySort(ArrayList<String> listString){
		//asc
		String prev = ""
		for (final String current: listString) {
			println("prev:"+prev+"curr:"+current)
			//bawah (curr) harus lebih besar dari atas (prev) supaya true
			if (current.compareTo(prev) < 0){
				return false;
			}
			prev = current
		}

		return true;

	}

	@Keyword
	public verifySortDescending(ArrayList<String> listString){
		String prev = ""
		for (final String current: listString) {
			println("prev:"+prev+"curr:"+current)
			if(prev==""){
				prev = current
				continue
			}
			//bawah (curr) harus lebih kecil dari atas (prev) supaya true
			if (current.compareTo(prev) > 0){
				return false;
			}
			prev = current
		}

		return true;
	}

	@Keyword
	public verifyPaging(ArrayList<String> dataPageOne, ArrayList<String> dataPageTwo){
		ArrayList<String> commonData = new ArrayList<>()
		// Find common elements
		for (String temp : dataPageOne) {
  
			// Check if element is present in page 2 or not
			if (dataPageTwo.contains(temp)) {
  
				//If present, add it to commonData
				commonData.add(temp);
			}
		}
  
		if(commonData.size()==0){
			return true
		}
		else if(commonData.size()>0){
			return false
		}
	}
}
