$( document ).ready(function() {
	var app = new Vue({
	  el: '#character-form-app',
	  data: {
	    message: 'Hello Vue!',
	    races: [],
	    race : 'human',
	    kind : ''
	  }
	});
});