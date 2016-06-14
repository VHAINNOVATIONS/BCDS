//######
//## This work is licensed under the Creative Commons Attribution-Share Alike 3.0 
//## United States License. To view a copy of this license, 
//## visit http://creativecommons.org/licenses/by-sa/3.0/us/ or send a letter 
//## to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
//######

(function($){
	
    var defaults = {
			inactivity: 1200000, //20 Minutes
			noconfirm: 10000, //10 Seconds
			sessionAlive: 30000, //10 Minutes
			redirect_url: '/js_sandbox/',
			click_reset: true,
			alive_url: '/js_sandbox/',
			logout_url: '/js_sandbox/',
		    redirect_handler: null
		}
    
    //##############################
    //## Private Variables
    //##############################
    var framewindow;
    var opts;
    var liveTimeout, confTimeout, sessionTimeout;
    var modal = "<div id='modal_pop'><p>You are about to be signed out due to inactivity.</p></div>";
    //##############################
    //## Private Functions
    //##############################
    var start_liveTimeout = function()
    {
      clearTimeout(liveTimeout);
      clearTimeout(confTimeout);
      liveTimeout = setTimeout(logout, opts.inactivity);

      if(opts.sessionAlive)
      {
        clearTimeout(sessionTimeout);
        sessionTimeout = setTimeout(keep_session, opts.sessionAlive);
      }
    }
    
    var logout = function()
    {

		if(opts.redirect_handler)
		{
				//alert("Using custom redirect handler");
				confTimeout = setTimeout(forceLogout, opts.noconfirm);
		}
		else
		{
				//alert("Using standard redirect handler");
				confTimeout = setTimeout(redirect, opts.noconfirm);
		}
      
      $(modal).dialog({
        buttons: {"Stay Logged In":  function(){
          $(this).dialog('close');
          stay_logged_in();
        }},
        modal: true,
        hide: 'fade',
        stack: true,
        open: function() {
        	var btnConfirm = $('.ui-dialog-buttonpane').find('button:contains("Stay Logged In")');
        	btnConfirm.width(btnConfirm.width() + 55);        
        },         
        title: 'Logout due to inactivity'
      }).height('auto').dialog('moveToTop');

    }
    
    forceLogout = function()
    {
    	window.onbeforeunload = null;

    	if(framewindow)
    		framewindow.onbeforeunload = null;
    	
    	custom_redirect();
    }

	var custom_redirect = function()
	{
	}

    var redirect = function()
    {
      if(opts.logout_url)
      {
        $.post(opts.logout_url);
      }
      window.location = opts.redirect_url;
    }
    
    var stay_logged_in = function(el)
    {
      start_liveTimeout();
      if(opts.alive_url)
      {
        $.get(opts.alive_url);
      }
    }
    
    var keep_session = function()
    {
      $.get(opts.alive_url);
      clearTimeout(sessionTimeout);
      sessionTimeout = setTimeout(keep_session, opts.sessionAlive);
    } 

    $.fn.startSessionTimeout = function(currentWindow) {

        start_liveTimeout();
        framewindow = currentWindow;

    }
    
    $.fn.idleTimeout = function(options) {
    
    //###############################
    //Build & Return the instance of the item as a plugin
    // This is basically your construct.
    //###############################
    return this.each(function() {
      obj = $(this);
      
      opts = $.extend(defaults, options);
      
      if(opts.redirect_handler)
      {
			  custom_redirect = opts.redirect_handler;
      }

      start_liveTimeout();

      // VBMS-R Done in util.js
      // $('body').bind('ajaxSend', function(event) { start_liveTimeout(); });

      if(opts.click_reset)
      {
        //$(document).bind('click', start_liveTimeout);
        //$('body').bind('click', start_liveTimeout);
      }
      if(opts.sessionAlive)
      {
        keep_session();
      }
    });
    
 };
})(jQuery);

