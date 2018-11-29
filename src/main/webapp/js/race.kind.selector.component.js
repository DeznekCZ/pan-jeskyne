/**
 * 
 */
$( document ).ready(function() {
	Vue.component('race-kind-selector', {
		props : ["races", "value", "initRace", "initKind", "kindsUrl", "racePlaceholder", "raceLabel", "kindPlaceholder", "kindLabel", "raceFieldName", "kindFieldName"],
		data: function () {
			return {
				race: this.initRace,
				kind: this.initKind,
				kinds : []
			}
		},
		created: function() {
			this.updateKinds(this.initRace);
		},
		methods: {
			onInput : function(value) {
				this.$emit('input', {race : this.race, kind : this.kind});
			},
			onRaceInput : function(value) {
				this.kind = undefined;
				this.onInput(value);
				this.updateKinds(value);
			},
			updateKinds : function(race) {
				var self = this;
				if(!race) {
					return;
				}
				axios.get(this.kindsUrl, {
				    params: {
				      raceCodename: race
				    }
				  })
				  .then(function (response) {
				    if(response.data.success) {
				    	self.kinds = response.data.kinds;
				    }
				  })
				  .catch(function (error) {
					  //TODO error
				    console.log(error);
				  }); 
			}
		},
		template: `	<div>
						<div class="five wide field">
							<label>{{raceLabel}}</label>
							<ui-dropdown classes="fluid search selection" :placeholder="racePlaceholder" :items="races" @input="onRaceInput"  
								v-model="race" :name="raceFieldName">
								<template slot="options">
									<slot name="races"></slot>
								</template>
							</ui-dropdown>
						</div>
						<div class="five wide field">
							<label>{{kindLabel}}</label>
		  					<ui-dropdown classes="fluid search selection" :placeholder="kindPlaceholder" :items="kinds" @input="onInput" 
		  						v-model="kind" :name="kindFieldName"></ui-dropdown>
		  				</div>
		  			</div>`
	})
});