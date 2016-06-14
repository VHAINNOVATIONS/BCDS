// Copyright 2010 Mal Curtis

if (typeof jQuery == 'undefined') throw("jQuery Required");

(function($){
	// Public General Plugin methods $.DirtyForms
	$.extend({
		DirtyForms: {
			debug : false,
			message : 'You\'ve made changes on this page which have not been saved. If you leave you will lose these changes.',
			title : 'Are you sure you want to do that?',
			dirtyClass : 'dirty',
			listeningClass : 'dirtylisten',
			ignoreClass : 'ui-state-default ui-state-hover',
			helpers : [],
			dialog : {
				refire : function(content, ev){
					// VBMS-R
					// $.facebox(content);
					var leaving = $(this).confirmLeaving();
					if(leaving)
					{
						decidingContinue();
					}
					else
					{
						decidingCancel();
					}
				},
				fire : function(message, title){
					var content = '<h1>' + title + '</h1><p>' + message + '</p><p><a href="#" class="ignoredirty button medium red continue">Continue</a><a href="#" class="ignoredirty button medium cancel">Stop</a>';
					// VBMS-R
					// $.facebox(content);
					var leaving = $(this).confirmLeaving();
					if(leaving)
					{
						decidingContinue();
					}
					else
					{
						decidingCancel();
					}
				},
				bind : function(){
					$('#facebox .cancel, #facebox .close, #facebox_overlay').click(decidingCancel);
					$('#facebox .continue').click(decidingContinue);
					$(document).bind('decidingcancelled.dirtyforms', function(){
					 	// Hacky way of manually removing the fuck out of Facebox
						$("#facebox").remove();
					 	$('#facebox_overlay').remove();
					 	$.facebox.settings.inited = false;
					});
				},
				stash : function(){
					var fb = $('#facebox');
					return ($.trim(fb.html()) == '' || fb.css('display') != 'block') ?
					   false :
					   $('#facebox .content').clone(true);
				},
				selector : '#facebox .content'
			},

			isDirty : function(){
				dirtylog('Core isDirty is starting ');
				var isDirty = false;
				$(':dirtylistening').each(function(){
					if($(this).isDirty()){
						isDirty = true;
						return true;
					}
				});

				$.each($.DirtyForms.helpers, function(key,obj){
					if("isDirty" in obj){
						if(obj.isDirty()){
							isDirty = true;
							return true;
						}
					}
				});

				dirtylog('Core isDirty is returning ' + isDirty);
				return isDirty;
			}


		}
	});

	// Create a custom selector $('form:dirty')
	$.extend($.expr[":"], {
		dirtylistening : function(a){
			return $(a).hasClass($.DirtyForms.listeningClass);
		},
		dirty : function(a){
			return $(a).hasClass($.DirtyForms.dirtyClass);
		}
	});

	// Public Element methods $('form').dirtyForm();
	$.fn.dirtyForms = function(){
		var core = $.DirtyForms;
		var thisForm = this;

		dirtylog('Adding forms to watch');
		bindExit();

		return this.each(function(e){
			dirtylog('Adding form ' + $(this).attr('id') + ' to forms to watch');
			$(this).addClass(core.listeningClass);
			$('input:text, input:password, input:checkbox, input:radio, textarea, select', this).change(function(){
				// VBMS-R
				if($(this).hasClass('dirtyformignore'))
				{
					//alert('Element has class dirtyformignore so will ignore ' + $(this));
					return;
				}
				$(this).setDirty();
			});
		});
	}

	$.fn.setDirty = function(){
		dirtylog('setDirty called');
		return this.each(function(e){
			$(this).addClass($.DirtyForms.dirtyClass).parents('form').addClass($.DirtyForms.dirtyClass);
		});
	}

	// VBMS-R
	$.fn.setClean = function(){
		dirtylog('setClean called');
		return this.each(function(e){
			$(this).removeClass($.DirtyForms.dirtyClass).parents('form').removeClass($.DirtyForms.dirtyClass);
		});
	}

	// Returns true if any of the supplied elements are dirty
	$.fn.isDirty = function(){
		var isDirty = false;
		var node = this;
		this.each(function(e){
			if($(this).hasClass($.DirtyForms.dirtyClass)){
				isDirty = true;
				return true;
			}
		});
		$.each($.DirtyForms.helpers, function(key,obj){
			if("isNodeDirty" in obj){
				if(obj.isNodeDirty(node)){
					isDirty = true;
					return true;
				}
			}
		});

		dirtylog('isDirty returned ' + isDirty);
		return isDirty;
	}

	// VBMS-R
	$.fn.confirmLeaving = function() {
		var answer = confirm("Are you sure you want to navigate away from this page? \n \n You've made changes on this page which have not been saved.  If you leave you will lose these changes.  \n\n Press OK to continue, or Cancel to stay on the current page.")
		if (answer){
			$(this).setClean();
			return true;
		}
		else{
			return false;
		}
	}

	// Private Properties and Methods
	var settings = $.extend({
		exitBound : false,
		formStash : false,
		dialogStash : false,
		deciding : false,
		decidingEvent : false,
		currentForm : false,
		hasFirebug : "console" in window && "firebug" in window.console,
		hasConsoleLog: "console" in window && "log" in window.console
	}, $.DirtyForms);

	dirtylog = function(msg){
		if(!$.DirtyForms.debug) return;
		msg = "[DirtyForms] " + msg;
		settings.hasFirebug ?
			console.log(msg) :
			settings.hasConsoleLog ?
				window.console.log(msg) :
				alert(msg);
	}
	bindExit = function(){
		if(settings.exitBound) return;
		
		$('a').live('click',aBindFn);
		$('form').live('submit',formBindFn);
		$(window).bind('beforeunload', beforeunloadBindFn);
		settings.exitBound = true;
	}

	// VBMS-R
	getEventTarget = function(ev)
	{
		// VBMS-R
		var target;
		if(ev.target)
			target = ev.target;
		else if(ev.srcElement)
			target = ev.srcElement;
		else
			target = null;
		
		return target;
		
	}
	
	// VBMS-R
	ignoreDirtyForThisForm = function(val)
	{
		var arr = document.getElementsByName(val);
		if(arr.length == 0)
		{
			return false;
		}
		else
		{
			return true;
		}

	}
	
	aBindFn = function(ev){
		
		if(ignoreDirtyForThisForm("nodirtyformcheck"))
			return false;
		
		// VBMS-R
		var target = getEventTarget(ev);
		var dateParentClassNames = null;
		if(ev.currentTarget && ev.currentTarget.className) {
			var clsName = ev.currentTarget.className;
		    if(clsName.indexOf('ui-datepicker-prev') > -1 || clsName.indexOf('ui-datepicker-next') > -1) {
		    	return;
		    }
		}
		if(target)
		{
			var classNames = target.className;
			
			 //Looking through file history, this logic is intended to prevent form submission when selecting a value from
	         //a datepicker.  Probably not the right place to do this as is it application logic, not whether or not the form has been modified
		    var childOfDatePicker = $(target).parents('.ui-datepicker').length > 0;
			
			if( (classNames.indexOf("dirtyformignore")>-1) ||
			(classNames.indexOf("ui-state-default ui-state-hover")>-1) ||
			(classNames.indexOf("ui-state-default ui-state-active")>-1) ||
			(classNames.indexOf("ui-state-default ui-state-highlight")>-1) || childOfDatePicker) {     
				return;
			}
			var alt = target.alt;
			if(alt)
			{
				if( (alt.indexOf("Glossary")>-1) || (alt.indexOf("Spell Check")>-1) || (alt.indexOf("Advanced Editor")>-1) )
				{
					return;
				}
			}
		}
		
		 bindFn(ev);
	}

	formBindFn = function(ev){

		// VBMS-R
		if(ignoreDirtyForThisForm("nodirtyformsubmitcheck"))
			return false;
		
		var target = getEventTarget(ev);		
		
		if(target)
		{
			var classNames = target.className;

			if( (classNames.indexOf("dirtyformignore")>-1) ||
			(classNames.indexOf("datefield")>-1) || (classNames.indexOf("datevalidation")>-1))
				return;
		}

		settings.currentForm = this;
		bindFn(ev);
	}

	beforeunloadBindFn = function(ev){
		var result = bindFn(ev);

		if(result && settings.doubleunloadfix != true){
			dirtylog('Before unload will be called, resetting');
			settings.deciding = false;
		}

		settings.doubleunloadfix = true;
		setTimeout(function(){settings.doubleunloadfix = false;},200);

		// VBMS-R
		if(result === false)
		{
			// VBMS-R
			// Nothing is dirty.  Can't return anything, even null, lest the browser will popup the warning dialog
			var someVar = 'someValue';
		}
		else
		{
			return result;
		}
	}

	bindFn = function(ev){
		// VBMS-R
		//dirtylog('Entering: Leaving Event fired, type: ' + ev.type + ', element: ' + ev.target + ', class: ' + $(ev.target).attr('class') + ' and id: ' + ev.target.id);

		if(ev.type == 'beforeunload' && settings.doubleunloadfix){
			dirtylog('Skip this unload, Firefox bug triggers the unload event multiple times');
			settings.doubleunloadfix = false;
			return false;
		}
		
		if($(ev.target).hasClass(settings.ignoreClass)){
			dirtylog('Leaving: Element has ignore class');
			if(!ev.isDefaultPrevented()){
				// VBMS-R
				//clearUnload();
				var someVar = 'someValue'; 
			}
			return false;
		}

		if(settings.deciding){
			dirtylog('Leaving: Already in the deciding process');
			return false;
		}

		if(ev.isDefaultPrevented()){
			dirtylog('Leaving: Event has been stopped elsewhere');
			return false;
		}

		if(!settings.isDirty()){
			dirtylog('Leaving: Not dirty');
			if(!ev.isDefaultPrevented()){
				clearUnload();
			}
			return false;
		}

		if(ev.type == 'submit' && $(ev.target).isDirty()){
			dirtylog('Leaving: Form submitted is a dirty form');
			if(!ev.isDefaultPrevented()){
				clearUnload();
			}
			return true;
		}

		settings.deciding = true;
		settings.decidingEvent = ev;
		dirtylog('Setting deciding active');

		if(settings.dialog !== false)
		{
			dirtylog('Saving dialog content');
			settings.dialogStash =settings.dialog.stash();
			dirtylog(settings.dialogStash);
		}

		// Callback for page access in current state
		$(document).trigger('defer.dirtyforms');

		if(ev.type == 'beforeunload'){
			//clearUnload();
			dirtylog('Returning to beforeunload browser handler with: ' + settings.message);
			return settings.message;
		}
		if(!settings.dialog) return;

		ev.preventDefault();
		ev.stopImmediatePropagation();

		if($(ev.target).is('form') && $(ev.target).parents(settings.dialog.selector).length > 0){
			dirtylog('Stashing form');
			settings.formStash = $(ev.target).clone(true).hide();
		}else{
			settings.formStash = false;
		}

		dirtylog('Deferring to the dialog');		
		settings.dialog.fire(settings.message, settings.title);
		settings.dialog.bind();
	}

	decidingCancel = function(ev){
		//VBMS-R
		//ev.preventDefault();
		$(document).trigger('decidingcancelled.dirtyforms');
		if(settings.dialog !== false && settings.dialogStash !== false)
		{
			dirtylog('Refiring the dialog with stashed content');
			settings.dialog.refire(settings.dialogStash.html(), ev);
		}
		$(document).trigger('decidingcancelledAfter.dirtyforms');
		settings.dialogStash = false;
		settings.deciding = settings.currentForm = settings.decidingEvent = false;
	}

	decidingContinue = function(ev){
		//VBMS-R
		//ev.preventDefault();
		settings.dialogStash = false;
		$(document).trigger('decidingcontinued.dirtyforms');
		refire(settings.decidingEvent);
	}

	clearUnload = function(){
		// I'd like to just be able to unbind this but there seems
		// to be a bug in jQuery which doesn't unbind onbeforeunload
		dirtylog('Clearing the beforeunload event');
		$(window).unbind('beforeunload', beforeunloadBindFn);
		window.onbeforeunload = null;
	}

	refire = function(e){
		$(document).trigger('beforeRefire.dirtyforms');
		switch(e.type){
			case 'click':
				dirtylog("Refiring click event");
				var event = new jQuery.Event('click');
				$(e.target).trigger(event);
				if(!event.isDefaultPrevented()){
					dirtylog('Sending location to ' + $(e.target).attr('href'));
					location.href = $(e.target).attr('href');
					return;
				}
				break;
			default:
				dirtylog("Refiring " + e.type + " event on " + e.target);
				var target;
				if(settings.formStash){
					dirtylog('Appending stashed form to body');
					target = settings.formStash;
					$('body').append(target);
				}
				else{
					target = $(e.target);
					if(!target.is('form'))
						target = target.closest('form');
				}
				target.trigger(e.type);
				break;
		}
	}

})(jQuery);
