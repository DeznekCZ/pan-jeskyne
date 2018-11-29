/**
 * 
 */
$( document ).ready(function() {
	Vue.component('ui-dropdown', {
		props : ['value', 'placeholder', 'items', 'classes', 'name'],
		mounted: function () {
			var self = this;
			$(this.$el).dropdown({
				clearable: true,
				onChange : function(value, text, $choice) {
					// pri zmene hodnoty vyslat event pro vue
					self.$emit('input', value);
				}
			})
			// inicializuj vybranou hodnotu
			$(this.$el).dropdown('set selected', this.value);
		},
		watch: { 
			value: function(newVal, oldVal) { // watch it
				if(newVal) {
					$(this.$el).dropdown('set selected', newVal);
				} else {
					$(this.$el).dropdown('clear');
				}
			}
	    },
		template:  `<div class="ui dropdown" :class="classes">
						<input type="hidden" :name="name">
					  	<i class="dropdown icon"></i>
					  	<div class="default text">{{placeholder}}</div>
					  	<div class="menu">
					    	<div class="item" v-for="item in items" :data-value="item.value" :data-text="item.text">
					    		{{item.text}}
					    	</div>
					    	<slot name="options"></slot>
					  	</div>
					</div>`,
	})
});