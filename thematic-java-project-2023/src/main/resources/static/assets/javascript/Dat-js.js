function setIma(main,child){
    var path = document.getElementById(child).getAttribute("src");
    document.getElementById(main).setAttribute("src",path);
    console.log(path);
}

const ima = document.querySelector(".imag2")
console.log(ima);
ima.addEventListener("click",function(){
    var path = document.getElementById("one").getAttribute("src");
    document.getElementById("main-img").setAttribute("src",path);

})

const ima2 = document.querySelector(".imag1")
console.log(ima2);
ima2.addEventListener("click",function(){
    var path = document.getElementById("zero").getAttribute("src");
    document.getElementById("main-img").setAttribute("src",path);

})
const ima3 = document.querySelector(".imag3")
console.log(ima3);
ima3.addEventListener("click",function(){
    var path = document.getElementById("two").getAttribute("src");
    document.getElementById("main-img").setAttribute("src",path);

})

const ima4 = document.querySelector(".imag4")
console.log(ima4);
ima4.addEventListener("click",function(){
    var path = document.getElementById("three").getAttribute("src");
    document.getElementById("main-img").setAttribute("src",path);

})

const ima5 = document.querySelector(".imag5")
console.log(ima5);
ima5.addEventListener("click",function(){
    var path = document.getElementById("four").getAttribute("src");
    document.getElementById("main-img").setAttribute("src",path);

})

const ima6 = document.querySelector(".imag6")
console.log(ima6);
ima6.addEventListener("click",function(){
    var path = document.getElementById("five").getAttribute("src");
    document.getElementById("main-img").setAttribute("src",path);

})

const ima7 = document.querySelector(".imag7")
console.log(ima7);
ima7.addEventListener("click",function(){
    var path = document.getElementById("six").getAttribute("src");
    document.getElementById("main-img").setAttribute("src",path);

})