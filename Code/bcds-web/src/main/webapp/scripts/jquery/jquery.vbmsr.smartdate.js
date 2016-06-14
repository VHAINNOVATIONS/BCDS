(function( $ ){
	  
	  /**
	   * This function ties together SmartDate and DatePicker.  With DatePicker, the user
	   * can pick a date in the widget, or can type in a date in a text box.  This function will parse,
	   * validate, and modify the date from DatePicker into a standard format to display in the text box.
	   * 
	   * Input: newdate is the date value being set in DatePicker.  If set, then we are to validate it
	   * before it's set in the DatePicker text box.  If null, then we are to validate the value
	   * already in the DatePicker text box.
	   */
	  $.fn.smartDate = function(newdate){

		  // By default, allow for mm/-dd/-yy format coming from DatePicker, but
		  // don't allow for that format from the user typing it in.  This is because we can't
		  // tell DatePicker to use either / or -, so we allow for both
		  var opts = {
				allowConsecutiveSeparators: true
			};
	  
		  var vrDate;
		  
		  if(!newdate)
		  {
			  // newdate is null, validate the value already in the DatePicker text box
			  opts.allowConsecutiveSeparators = false;
			  vrDate = new VR_DateObject(this.val());
		  }
		  else
		  {
			  // validate the value being set in the DatePicker text box
			  vrDate = new VR_DateObject(newdate);
		  }
		  
		  // if it's a valid date, format it, otherwise don't do anything
		  if (vrDate.IsDate(opts))
		  {
			  this.val(vrDate.FormattedDate(opts));
		  }
	  };
	  
	  GetKeyPress = function(txtBox, e) {
		   //------------------------------------------------------------------------
	   // Purpose: Restricts the text field to Alpha numeric
	   // Inputs : Text txtBox object, event  e object
	   // Returns: Bool()
	   //------------------------------------------------------------------------
	   var iCharCode;
	   var sKeyChar;
	
	   // Check for Browser-dependency......................
	   //           IsIEBrowser ? IE-Browser: else Netscape.
	   iCharCode = (e.which == null) ? e.keyCode : e.which;
	   sKeyChar = String.fromCharCode(iCharCode);
	
	   //alert("GetKeyPress: Got It data=" + iCharCode + ", sKeyChar = [" + sKeyChar + "], txtBox.value=" + txtBox.value);
	
	   bIsValid = ((iCharCode >= 48) && (iCharCode <= 57));  // IsNumeric?
	   switch (iCharCode) {
	      case 13:    // <enter>
	      case 47:    // <slash> /
	      case 45:    // <hyphen> -
	         bIsValid = true;
	         break;
	   }
	
	   //    IsValid?
	   return bIsValid;
	}
	
	GetKeyDown= function(txtBox, e) {
	   //------------------------------------------------------------------------
	   // Purpose: Restricts the text field to Alpha numeric
	   // Inputs : Text txtBox object, event  e object
	   // Returns: Bool()
	   //------------------------------------------------------------------------
	   var iCharCode;
	   var sKeyChar;
	   var bIsValid;
	
	   // Check for Browser-dependency......................
	   //           IsIEBrowser ? IE-Browser: else Netscape.
	   iCharCode = (e.which == null) ? e.keyCode : e.which;
	   sKeyChar = String.fromCharCode(iCharCode);
	
	   //alert("GetKeyDown: Got It data=" + iCharCode + ", sKeyChar = [" + sKeyChar + "], txtBox.value=" + txtBox.value);
	
	   bIsValid = (fnGetDateDisplayChar(iCharCode) != "");  // IsNumeric, SLASH or HYPHEN?
	   switch (iCharCode) {
	      case 8:     // <backspace>
	      case 9:     // <tab>
	      case 13:    // <enter>
	      case 16:    // <shift>
	      case 191:   // <slash> /
	      case 111:   // <slash> /
	      case 189:   // <hyphen> -
	      case 109:   // <hyphen> - 
	      case 37:    // <left-arrow>
	      case 39:    // <right-arrow>
	      case 35:    // <end>
	      case 36:    // <home>
	      case 45:    // <insert> 
	      case 46:    // <delete> 
	         bIsValid = true;
	         break;
	   }
	   // Test if length is 10 or more.
	   if (fnGetDateDisplayChar(iCharCode) != "") {  // IsNumeric, SLASH or HYPHEN?
	      // Determine the position of the caret within the control's existing text.
	      var iDatePos = fnGetSelStart(txtBox);
	      var sDateString = txtBox.value;
	      var sLeft = "";
	      var sRight = "";
	      var sChar1 = "";
	      var sChar2 = "";
	      if (sDateString.length >= 10) {
	         if (iDatePos >= 10) {
	            // Adjust the insertion-character to the last character position as 9.
	            iDatePos = 9;
	         }
	         // Attempt to overwrite the character that is already in the string.
	         sChar1 = sDateString.substring(iDatePos, iDatePos + 1);
	         //if (/[0-9]/.text(sChar1)==false) iDatePos+=1;
	
	         // Change the character.
	         sChar2 = fnGetDateDisplayChar(iCharCode);
	         var sLeft, sRight;
	         sLeft = sDateString.substring(0, iDatePos);
	         sRight = sDateString.substring(iDatePos + 1);
	         txtBox.value = sLeft.concat(sChar2, sRight);
	
	         sbSetSelStart(txtBox, iDatePos + 1)
	         bisValid = false;
	      } // IsNumeric?
	   } else {
	   }
	
	   //    IsValid?
	   return bIsValid;
	}
	
	// Functions dealing with Character-Codes.
	fnGetDateDisplayChar = function(p_iCharCode) {
	   //------------------------------------------------------------------------
	   // Purpose: Gets the DisplayCharacter for the 'p_iCharCode'.
	   // Inputs : 'p_iCharChar' is the is a value obtained from KeyDown() event
	   //          by capturing the keystroke.
	   // Returns: If a NUMBER or SLASH or HYPHEN, return the character as a string.
	   //          else, return "" <empty-string>.
	   //------------------------------------------------------------------------
	
	   // Determine if a number (0-9) -- consider the NUMBER-PAD and TYPEWRITER.
	   if ((p_iCharCode>=48) && (p_iCharCode<=(48+9))) {  // TYPEWRITER.
	      return String.fromCharCode(p_iCharCode);
	   }
	   if ((p_iCharCode>=96) && (p_iCharCode<=(96+9))) {  // NUMBER-PAD.
	      return String.fromCharCode(p_iCharCode-48);
	   }
	   
	   switch (p_iCharCode) {
	      case 191:   // <slash> /
	      case 111:   // <slash> /
	         return "/";
	         break;
	      case 189:   // <hyphen> -
	      case 109:   // <hyphen> - 
	         return "-";
	         break;
	   }
	   // If we made it to here, the character is NOT a DATE-display character.
	   // Return <empty-string> "".
	   return "";
	}
	
	// Functions dealing with CURSOR location.
	fnGetSelStart = function(p_oThis) {
	   //------------------------------------------------------------------------
	   // Purpose: Obtains the typing-cursor position for 'p_oThis' control.
	   // Inputs : Text txtBox object.
	   // Returns: The position of the typing-cursor.
	   //------------------------------------------------------------------------
	   if (p_oThis.createTextRange) {
	      var r = document.selection.createRange().duplicate();
	      r.moveEnd('character', p_oThis.value.length);
	      if (r.text == '') return p_oThis.value.length;
	      return p_oThis.value.lastIndexOf(r.text);
	   } else return p_oThis.selectionStart;
	}
	
	sbSetSelStart = function(p_oThis, p_iPos) {
	   //------------------------------------------------------------------------
	   // Purpose: Sets the typing-cursor position for 'p_oThis' control.
	   // Inputs : Text txtBox object, position of the intended typing-cursor.
	   // Returns: TRUE, always.
	   //------------------------------------------------------------------------
	   var iOffset;
	
	   if (p_oThis.createTextRange) {
	      var oRng = document.selection.createRange();
	      iOffset = oRng.moveEnd("textedit", -1);
	      //alert("iOffset="+iOffset);
	      iOffset = oRng.moveStart("textedit", -1);
	      iOffset = oRng.select();
	      iOffset = oRng.collapse(true);
	      iOffset = oRng.moveEnd("character", p_iPos);
	      iOffset = oRng.moveStart("character", p_iPos);
		      iOffset = oRng.select();
		   }
		   return true;
		}
	  
		VR_DateObject = function (p_sDateString) {	/* Constructor for the DateObject.
		 *	Parameters:	p_sDateString	- A string value that
		 *										  may or MAY NOT be a valid DATE string.
		 * Public Properties:
		 *		toString				- Returns 'p_sDateString'.
		 *		IsBlank				- Returns TRUE, if the 'p_sDateString' contains 0 or more <blanks> (only blanks or empty).
		 *		IsDate				- Returns TRUE, if the 'p_sDateString' is a valid CALENDAR date.
		 *		FormattedDate		- Returns the date-string in MM/DD/YYYY -- only if valid, else returns 'p_sDateString'.
		 *		MonthValue			- Returns the month value as parsed from 'p_sDateString' may return 0, if not parsed to a date.
		 *		DayValue				- Returns the day value as parsed from 'p_sDateString' may return 0, if not parsed to a date.
		 *		YearValue			- Returns the year value as parsed from 'p_sDateString' may return 0, if not parsed to a date.
		 *		DateObject			- Returns a DATE-object, only if a valid date, else returns <undefined>.
		 ******************************************************************************************************/
	   var m_osDateString;
	   var m_sDateString;
	   var m_iMM;
	   var m_iDD;
	   var m_iYYYY;
	
	   // Save the parameter as the DATE-STRING for this instance.
	   m_osDateString = new String(p_sDateString);
	   m_sDateString = m_osDateString.valueOf();
	
	   // Initialize class variables.
	   m_iMM = 0;
	   m_iDD = 0;
	   m_iYYYY = 0;
	
	   // Class Property functions.
	//   this.m_iMM = MonthValue;
	//   this.m_iDD = DayValue;
	//   this.m_iYYYY = YearValue;
	   this.IsBlank = IsBlank;
	   this.toString = toString;
	   this.IsDate = IsDate;
	   this.FormattedDate = FormattedDate;
	   this.MonthValue = MonthValue;
	   this.DayValue = DayValue;
	   this.YearValue = YearValue;
	   this.DateObject = DateObject;
	
	   VR_DateObject.prototype.SetValue = function(p_iValue) { this.value = p_iValue; }
	
	   function toString()  { return m_sDateString; }
	   function IsBlank()   { return fnIsStrBlank(m_sDateString); }
	   function IsDate(opts)    {
	      if (IsBlank()) return false;
	      var iMM = new oParam();
	      var iDD = new oParam();
	      var iYYYY = new oParam();
	      if (!fnParseStringToDateParts(m_sDateString, iMM, iDD, iYYYY, opts)) return false;
	      m_iMM = iMM.getValue();
	      m_iDD = iDD.getValue();
	      m_iYYYY = iYYYY.getValue();
	      return (fnAreDatePartsValid(m_iMM, m_iDD, m_iYYYY));
	   }
	   function FormattedDate(opts) {
	      if (IsDate(opts)) return fnFmtDate(m_iMM, m_iDD, m_iYYYY);
	   }
	   function MonthValue() { return m_iMM; }
	   function DayValue()   { return m_iDD; }
	   function YearValue()  { return m_iYYYY; }
	   function DateObject() { return (IsDate() ? new Date(m_iYYYY, m_iMM - 1, m_iDD, 0, 0, 0) : undefined); }
	
	   //VR_DateObject.prototype.method=dummymethod
	
	}
	
	oParam = function() {
	   this.array = new Array(1);
	   this.setValue = function(val) { this.array[0] = val; }
	   this.getValue = function()    { return this.array[0]; }
	}
	
	fnIsStrBlank = function(p_sText) {	
	   /*	Returns TRUE if 'p_sText' contains zero or more <spaces> only.
		 *	else FALSE.
	 	 ***************************************************************/
	   var reIsBlank = /^\s*$/
	   return (reIsBlank.test(p_sText));
	}
	
	fnParseStringToDateParts = function(p_sTextString, r_MM, r_DD, r_YYYY, opts) {	
	   /*	Parses the 'p_sTextString' to see if it can be interpretted as a DATE.
		 * Parameters:	p_sTextString	- A string that is to be determined if the format is an allowable DATE string.
		 *					r_MM				- A "month" value returned IF the 'p_sTextString' is considered a DATE string.
		 *										  This value may or MAY NOT be a valid month value.
		 *					r_DD				- A "day" value returned IF the 'p_sTextString' is considered a DATE string.
		 *										  This value may or MAY NOT be a valid day value.
		 *					r_YYYY			- A "year" value returned IF the 'p_sTextString' is considered a DATE string.
		 *										  This value may or MAY NOT be a valid year value.
		 *	The allowable formats of the DATE-string are determined by RegExp.
		 *	If the 'p_sTextString' matches the RegExp formats, the parameters 'r_MM, r_DD, r_YYYY' are returned.
		 * The values of 'r_MM, r_DD, r_YYYY' are NOT checked for valid date-values (month, date, year).
		 *	Returns TRUE if the string is in DATE format, else FALSE.
		 *******************************************************************/
	   var reDigit = /[0-9]/
	   var reDateFormat1 = /^\d{1,2}(\-|\/)\d{1,2}(\-|\/)\d{4}$/   // mm/dd/yyyy or mm-dd-yyyy
	   var reDateFormat11 = /^\d{1,2}(\/)\d{1,2}(\/)\d{4}$/   // mm/dd/yyyy
       var reDateFormat12 = /^\d{1,2}(\-)\d{1,2}(\-)\d{4}$/   // mm-dd-yyyy	   
       var reDateFormat2 = /^\d{8}$/                               // mmddyyyy    	   
	   var reDateFormat3 = /^\d{1,2}(\-|\/)\d{4}$/   // mm/yyyy or mm-yyyy
	   var reDateFormat31 = /^\d{1,2}(\/)\d{4}$/   // mm/yyyy
	   var reDateFormat32 = /^\d{1,2}(\-)\d{4}$/   // mm-yyyy		   
	   var reDateFormat4 = /^\d{6}$/                               // ddyyyy
	   var reDateFormat5 = /^\d{1,2}(\/-)\d{1,2}(\/-)\d{4}$/   // mm/-dd/-yyyy
	   var sMM = "", sDD = "", sYYYY = "", sYY = "";
	   var iMM, iDD, iYYYY;
	   var sText, saMDY_Array;
	   var iDateFmtType;
	   var allowConsecutiveSeparators;
	
	   // Initialize.
	   sText = p_sTextString;
	   allowConsecutiveSeparators = false;
	   
	   if(opts)
	   {
		   allowConsecutiveSeparators = opts.allowConsecutiveSeparators;
	   }
	
	   // Test for either of TWO different date formats (mm/dd/yyyy and ddmmmyyyy)
	   if (reDateFormat11.test(sText)) {
	      iDateFmtType = 11;
	   } else if (reDateFormat12.test(sText)) {
		  iDateFmtType = 12;
	   } else if (reDateFormat2.test(sText)) {
	      iDateFmtType = 2;
	   } else if (reDateFormat31.test(sText)) {
	      iDateFmtType = 31;
	   } else if (reDateFormat32.test(sText)) {
		  iDateFmtType = 32;
	   } else if (reDateFormat4.test(sText)) {
	      iDateFmtType = 4;
	   } else if (allowConsecutiveSeparators && reDateFormat5.test(sText)) {
		  iDateFmtType = 5;
	   } else {
	      // The value does not conform to any of the date-format-types.
	      return false;
	   }
	
	   // If we made it to here, we need to parse the date-string into a DATE of mm/dd/yyyy or m/d/yyyy.
	   if (iDateFmtType == 11 || iDateFmtType == 12) {
	      // Parse as mm/dd/yyyy -or- as m/d/yyyy
	      sText = sText.replace(/\-/g, "/");   // Replace all MINUSes with SLASHes.
	      saMDY_Array = sText.split(/\//);    // Split into M/D/YYYY.
	      sMM = saMDY_Array[0];
	      sDD = saMDY_Array[1];
	      sYYYY = saMDY_Array[2];
	   }
	
	   if (iDateFmtType == 2) {
	      // Parse as mmddyyyy.
	      sMM = sText.substring(0, 2);
	      sDD = sText.substring(2, 4);
	      sYYYY = sText.substring(4);
	   }

	   if (iDateFmtType == 31 || iDateFmtType == 32) {
	      // Parse as mm/yyyy -or- as m/yyyy
	      sText = sText.replace(/\-/g, "/");   // Replace all MINUSes with SLASHes.
	      saMDY_Array = sText.split(/\//);    // Split into M/YYYY.
	      sMM = saMDY_Array[0];
	      sDD = 15;
	      sYYYY = saMDY_Array[1];
	   }

	   if (iDateFmtType == 4) {
	      // Parse as mmyyyy.
	      sMM = sText.substring(0, 2);
	      sDD = 15;
	      sYYYY = sText.substring(2,6);
	   }

	   // If we made it to here, we need to parse the date-string into a DATE of mm/-dd/-yyyy
	   if (allowConsecutiveSeparators && iDateFmtType == 5) {
	      // Parse as mm/dd/yyyy -or- as m/d/yyyy
	      sText = sText.replace(/\/-/g, "/");   // Replace all /- with /
	      saMDY_Array = sText.split(/\//);    // Split into M/D/YYYY.
	      sMM = saMDY_Array[0];
	      sDD = saMDY_Array[1];
	      sYYYY = saMDY_Array[2];
	   }
	
	   // Now determine if the DATE is a valid calendar-date (number of DAYS, MONTHS, and YEAR).
	   // Convert the strings to numbers to be returned in the function parameters.
	   r_MM.setValue(Number(sMM).valueOf());
	   r_DD.setValue(Number(sDD).valueOf());
	   r_YYYY.setValue(Number(sYYYY).valueOf());
	
	   return true;
	}
	
	fnAreDatePartsValid = function(r_MM, r_DD, r_YYYY) {	/*	Determines whether the parameters are valid DATE values according to the CALENDAR.
		 *	The LEAP-YEAR and DAYS for each month are taken into account.
		 * Parameters:	r_MM				- A "month" value - to be valid, must be in the range of 1-12.
		 *					r_DD				- A "day" value - to be valid, must be in the range of 1-31
		 *										  and be valid for the MONTH and YEAR.
		 *					r_YYYY			- A "year" value - to be valid, must be in the range of 1800-2100.
		 *	Returns TRUE if the parameters 'r_MM, r_DD, r_YYYY' represent a valid CALENDAR date, else FALSE.
		 *******************************************************************/
	   var bIsLeapYear;
	   var iaDaysPerMonth;
	   var bIsDate;
	
	   // Determine if a LEAP-YEAR.
	   bIsLeapYear = (new Date(r_YYYY, 2 - 1, 29).getDate() == 29);
	   // Establish the "allowable" number of days per month.
	   iaDaysPerMonth = new Array(31, (bIsLeapYear ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	
	   // Validate YEAR, MONTH and DAY values.
	   bIsDate = false;
	   if ((r_YYYY >= 1800) && (r_YYYY <= 2100)) {
	      if ((r_MM >= 1) && (r_MM <= 12)) {
	         bIsDate = ((r_DD >= 1) && (r_DD <= iaDaysPerMonth[r_MM - 1]));
	      }
	   }
	
	   return bIsDate;
	}
	
	fnFmtDate = function (p_vMM, p_vDD, p_vYYYY) {	
	   /* Formats the date given 'p_vMM', 'p_vDD' and 'p_vYYYY' to MM/DD/YYYY format.
		 * Convertsthe parameters to STRING objects and formats as a string.
		 *	There is NO VALIDATION of the parameters.
		 * Returns the formatted-date-string.
		 *******************************************************************/
	   var sMM, sDD, sYYYY;
	
	   // Working with string-lenths, format the date.
	   sMM = new String(p_vMM);
	   if (sMM.length == 1) sMM = "0".concat(sMM);
	
	   sDD = new String(p_vDD);
	   if (sDD.length == 1) sDD = "0".concat(sDD);
	
	   sYYYY = new String(p_vYYYY);
	   return (sMM + "/" + sDD + "/" + sYYYY)
	}
	
	fnIsAValidDate = function (p_oThis, p_bIsBlankValid) { 
	   /*  Validates whether the contents of the 'p_oThis' entry box is a valid date.
	    *  Two types of entries are allowed M/D/YYYY -or- MM/DD/YYYY -or- MMDDYYYY (no separators).
	    *  Two SEPARATORS are permitted SLASH and MINUS (DASH) -- can be mixed.
	    *  If the date is VALID, the date is formatted as "MM/DD/YYYY".
	    *
	    *  Parameters: p_oThis         - a TEXTBOX object for a calendar DATE.
	    *              p_bIsBlankValid - a TRUE/FALSE whether the text may be <blank>/null/empty.
	    *  Returns:    TRUE if a valid 'calendar' date, else FALSE.
	    ************************************************************************/
	   // Test for valid DATE.
	   var sText, iDateFmtType = 0;
	   var reDigit = /[0-9]/
	   var reDateFormat1 = /^\d{1,2}(\-|\/)\d{1,2}(\-|\/)\d{4}$/   // mm/dd/yyyy or mm-dd-yyyy
	   var reDateFormat2 = /^\d{8}$/                               // mmddyyyy
	   var sMM = "", sDD = "", sYYYY = "", sYY = "";
	   var iMM, iDD, iYYYY;
	   var bIsDate, bIsLeapYear, bRet = false;
	   var iaDaysPerMonths;
	
	   // Initialize.
	   sText = p_oThis.value;
	
	   // Test for either of TWO different date formats (mm/dd/yyyy and ddmmmyyyy)
	   bRet = false;
	   if (reDateFormat1.test(sText)) {
	      iDateFmtType = 1;
	   } else if (reDateFormat2.test(sText)) {
	      iDateFmtType = 2;
	   } else {
	      // The value does not conform to any of the date-format-types.
	      // Determine if <blank> date is allowed, then it is OK, otherwise false.
	      if (p_bIsBlankValid) {
	         bRet = (fnIsStrBlank(sText) ? true : false);
	      }
	      return (bRet);
	   }
	
	   // If we made it to here, we need to parse the date-string into a DATE of mm/dd/yyyy or m/d/yyyy.
	   if (iDateFmtType == 1) {
	      // Parse as mm/dd/yyyy -or- as m/d/yyyy
	      sText = sText.replace(/\-/g, "/");   // Replace all MINUSes with SLASHes.
	      saMDY_Array = sText.split(/\//);    // Split into M/D/YYYY.
	      sMM = saMDY_Array[0];
	      sDD = saMDY_Array[1];
	      sYYYY = saMDY_Array[2];
	   }
	
	   if (iDateFmtType == 2) {
	      // Parse as mmddyyyy.
	      sMM = sText.substring(0, 2);
	      sDD = sText.substring(2, 4);
	      sYYYY = sText.substring(4);
	   }
	
	   // Now determine if the DATE is a valid calendar-date (number of DAYS, MONTHS, and YEAR).
	   // Convert the strings to numbers to be tested.
	   iMM = new Number(sMM);
	   iDD = new Number(sDD);
	   iYYYY = new Number(sYYYY);
	
	   // Determine if a LEAP-YEAR.
	   bIsLeapYear = (new Date(iYYYY, 2 - 1, 29).getDate() == 29);
	   // Establish the "allowable" number of days per month.
	   iaDaysPerMonth = new Array(31, (bIsLeapYear ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	
	   // Validate YEAR, MONTH and DAY values.
	   bIsDate = false;
	   if ((iYYYY >= 1800) && (iYYYY <= 2100)) {
	      if ((iMM >= 1) && (iMM <= 12)) {
	         bIsDate = ((iDD >= 1) && (iDD <= iaDaysPerMonth[iMM - 1]));
	      }
	   }
	
	   // If valid, format the date-string as MM/DD/YYYY and place into the control's value.
	   if (bIsDate) {
	      p_oThis.value = fnFmtDate(iMM, iDD, iYYYY);
	   }
	
	   return bIsDate;
	}
	
	evtFormatOnBlur = function (p_oThis) {
	   /*  Handles the ON-BLUR event for 'p_oThis' control.
	    *  Calls fnIsAValidDate()-function that determines and formats the valid date into the control.
	    *  Always returns TRUE.
	    *******************************************************************/
	   fnIsAValidDate(p_oThis, true);
	   return true;
	}

})( jQuery );
