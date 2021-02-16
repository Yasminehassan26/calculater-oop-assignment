<template>
<div class="body">
  <div class="calculator-grid">
    <div class="display ">
      <div class="previous" >{{ resultUp || 0 }}</div>
      <div class="current" >{{ resultDown || 0 }}</div>
      </div>
    <button v-on:click="chooseOp2('percent')" class="char">%</button>
    <button v-on:click="clearData"  class="char">CE</button>
    <button v-on:click="clearData" class="char">C</button>
    <button v-on:click="remove" class="char"> &#8592; </button>
    <button v-on:click="chooseOp2('inv')" class="char">1/x</button>
    <button v-on:click="chooseOp2('pow')" class="char">x&#178;</button>
    <button v-on:click="chooseOp2('root')" class="char"> &#8730;x </button>
    <button v-on:click="chooseOp('/')" class="char op">/</button>
    <button v-on:click="showNumber('7')" class="buttonS">7</button>
    <button v-on:click="showNumber('8')" class="buttonS">8</button>
    <button v-on:click="showNumber('9')" class="buttonS">9</button>
    <button v-on:click="chooseOp('*')" class="char op">x</button>
    <button v-on:click="showNumber('4')" class="buttonS">4</button>
    <button v-on:click="showNumber('5')" class="buttonS">5</button>
    <button v-on:click="showNumber('6')" class="buttonS">6</button>
    <button v-on:click="chooseOp('-')" class="char op">-</button>
    <button v-on:click="showNumber('1')" class="buttonS">1</button>
    <button class="buttonS" v-on:click="showNumber('2')">2</button>
    <button v-on:click="showNumber('3')" class="buttonS">3</button>
    <button v-on:click="chooseOp('+')" class="char op">+</button>
    <button v-on:click="negate()" class="char op">+/-</button>
    <button v-on:click="showNumber('0')" class="buttonS">0</button>
    <button v-on:click="dot('.')" class="char">.</button>
    <button v-on:click="chooseOp('=')" class="char equal">=</button>
  </div>
   </div>
</template>

<script>



import axios from 'axios';
export default {
  data(){
    return {
      resultDown:'',
      resultUp:'',
      operator:null,
      operatorS:null,
      operandChosen1:false,
      num1:null,
      num2:null,
      show:false,
      firstOp:false,
      ops:false,
      errors:false,
      equal:false,
      AfterEqual:false,
      entered:false,
      begin:true,
      here:false,
      temp:null,
      enteredop2:false

    }
  },

  methods:{
    mounted1()
    {
      axios.get("http://localhost:8085/op1", {
        params: {
          one: this.num1,
          two: this.num2,
          op:this.operator,
        }
      })

          .then(function (response){
            this.resultDown =response.data;
            if(this.resultDown==='E'){
              this.clearData()
              this.resultDown =response.data;
              this.errors=true
            }else{
              this.num1=this.resultDown}
          }.bind(this))
    },

    clearData(){
      this.AfterEqual=false,
          this.resultDown='',
          this.resultUp='',
          this.num1=null,
          this.operator=null,
          this.operatorS=null,
          this.operandChosen1=false,
          this.num2=null,
          this.show=false,
          this.firstOp=false,
          this.ops=false,
          this.errors=false,
          this.equal=false,
          this.begin=true,
          this.entered=false,
          this.here=false,
          this.temp=null,
      this.enteredop2=false

    },
    remove(){
      if(this.entered===false){
      this.resultDown=this.resultDown.substr(0,this.resultDown.length-1)}
    },
    negate(){
      if(this.begin===false){
      if(this.entered===false || this.equal===true){
      axios.get("http://localhost:8085/neg", {
        params: {
          one: this.resultDown,
        }
      })
          .then(function (response){
            this.resultDown =response.data;
            if(this.equal===true){
              this.num1=this.resultDown
            }
          }.bind(this))


      }}

    },


    showNumber: function (number) {
      if (this.begin === true) {
        this.begin = false
      }
      this.entered = false
      if (this.equal === false) {
        if (this.operandChosen1 && this.show) {
          this.resultDown = ''
          this.show = false
        }
        if (this.errors) {
          this.resultDown = ''
          this.errors = false
        }
        if(this.here===true){
          this.here=false
          this.resultDown = ''
        }
        if (this.resultDown === '0' && number === '0') {
          this.resultDown = '0'
        } else {
          if (this.resultDown==='' && number==='.'){
            this.resultDown = '0'
          }
          this.resultDown = `${this.resultDown}${number}`
        }
      } else {
        if (this.AfterEqual === false) {
          this.resultDown = ''
          this.AfterEqual = true
        }
        this.resultDown = `${this.resultDown}${number}`
      }

    },
    chooseOp: function (number) {
      if(this.begin===false){

        if(this.errors===false){
          if((this.entered===true && this.equal===false)) {
            if(number==='='){
              this.num2=this.num1
              this.mounted1()
              this.operandChosen1=false
              this.equal=true
            }
            else{
              this.operator = `${number}`
              this.resultUp = this.resultUp.substr(0, this.resultUp.length - 1)
              this.resultUp = `${this.resultUp}${number}`
            }
          }
          else if(this.entered===true && this.equal===true && number==='='){
            this.resultUp=this.resultUp.substr(0,this.resultUp.length-1)
            this.resultUp = `${this.resultUp}${number}`
          }

          else{
            if (this.operandChosen1 === false) {
              this.resultUp = `${this.resultDown}${number}`
              this.operandChosen1 = true
              this.equal = false
              if (this.firstOp === false) {
                this.num1 = this.resultDown
                this.firstOp = true
              }
              if (this.AfterEqual === true) {
                this.num1 = this.resultDown
              }
              if (this.here === true) {
                this.num1 = this.resultDown
                this.here=false
              }
              this.AfterEqual = false
              this.operator = `${number}`
              this.show = true


            } else {
              this.resultUp = `${this.resultUp}${this.resultDown}${number}`
              this.num2 = this.resultDown
              this.mounted1()
              if(number==='='){
                this.operandChosen1=false
                this.equal=true
              }else{
                this.operator = `${number}`}
              this.show=true

            }
            this.entered=true}

        }

      }
    },
    chooseOp2: function (number) {
      if(this.errors===false && this.begin===false){
           this.temp=this.resultDown
        axios.get("http://localhost:8085/op2", {
          params: {
            one: this.resultDown,
            op:number
          }
        })
            .then(function (response){
              this.resultDown =response.data;
              if(this.resultDown==='E'){
                this.clearData()
                this.resultDown =response.data;
                this.errors=true
              }
              if(this.equal===true ){
                this.num1=this.resultDown
              }
              else if (this.operandChosen1===true && this.entered===false){
                this.resultDown=response.data
                this.here=true

              }
              else if (this.entered===true){
                this.num1=this.temp
                this.num2=this.resultDown
                this.mounted1()
                this.resultUp = `${this.resultUp}${number}${this.num1}`
              }
            }.bind(this)) }
      else if(this.begin===true && number=='inv'){
        this.clearData()
        this.resultDown ='E';
        this.errors=true
      }

    },

    dot(number){
      if(this.begin===true){
        if (this.resultDown.indexOf('.')===-1) {
          this.showNumber(number)
        }}
      else if (this.entered===false){
        if (this.resultDown.indexOf('.')===-1) {
          this.showNumber(number)
        }
      }
    },

  },


}

</script>
<style>
.body{
  background-color:rgb(253, 255, 255);
  border:10px solid rgb(255, 255, 255) ;
  display:grid ;

}
.display{
  grid-column: 1/5;
  background-color: rgb(255, 255, 255);
  text-align: right;
  justify-content: space-around;
  padding: 20px;
  overflow:auto;


}
.display .previous{
color: rgba(97, 96, 96, 0.555);
font-size: 20px;
}
.display .current{
  color: rgb(0, 0, 0);
font-size: 30px;

}
.calculator-grid{
  display: grid;
  justify-content: center;
  align-items: center;
  grid-template-columns: repeat(4,100px);
  grid-template-rows: minmax(100px,auto) repeat(5,100px);
  background-color:rgb(48, 20, 100);
  font-size: 40px;
  border: 10px black;
  grid-gap: 5px;
  min-height: 80vh;
 margin: 0 auto;
 min-width: 450px;
 padding: 20px;
}

.buttonS{
  cursor: pointer;
  background-color:rgb(255, 255, 255)  ;
  border: 1px solid;
  font-size: 40px;
border-radius: 40px;
outline: white;
}
.char{
  background-color:rgb(255, 255, 255)  ;
  font-size: 40px;
  border: 1px solid;
    cursor: pointer;
border-radius: 15px;
outline: white;


}
.char:active {
  box-shadow: 0 5px rgb(255, 255, 255);
  transform: translateY(4px);
}
.char:hover {
  background-color: #08438f;
  color: white;
}
.equal{
  background-color: #36ff24;
  color: black;
   outline: white;
}
.equal:active {
  box-shadow: 0 5px rgb(255, 255, 255);
  transform: translateY(4px);
}
.op{
  color: red;
    cursor: pointer;
    outline: white;

}
.buttonS:hover {
  background-color: #008CBA;
  color: white;
}
.buttonS:active {
  box-shadow: 0 5px rgb(255, 255, 255);
  transform: translateY(4px);
}
.op:hover {
  background-color:  red;
  color: white;
}
.op:active {
  box-shadow: 0 5px rgb(255, 255, 255);
  transform: translateY(4px);
}
</style>