// search at top
$(function () {
    $(".input-text-search-top").click(() => {
        $(".contain-search-popular").css("display", "flex");
    });
    $(".contain-search").mouseleave(() => {
        $(".contain-search-popular").css("display", "none");
    });
});

$(function () {
    $(".bt-nav-collapse").click(() => {
        $(".navbar-collapse").slideToggle("slow");

    })
})
// display filter search when click {
$(function () {
    $(".toggle-filter-search").click(() => {
        $(".contain-filter-search").slideToggle("show");
    });
});


// payment method
$(function () {

    $("#payment-cod").click(() => {
        $(".payment-input").removeClass("payment-input-active")
        $("#contain-payment-cod").addClass("payment-input-active");
    })

    $("#payment-shoppe").click(() => {
        $(".payment-input").removeClass("payment-input-active")
        $("#contain-payment-shoppe").addClass("payment-input-active");
    })

    $("#payment-zalo").click(() => {
        $(".payment-input").removeClass("payment-input-active")
        $("#contain-payment-zalo").addClass("payment-input-active");
    })

    $("#payment-momo").click(() => {
        $(".payment-input").removeClass("payment-input-active")
        $("#contain-payment-momo").addClass("payment-input-active");
    })

});

function displayMenuNavigation() {
    let content = `<div class="nav-inner">
                        
                            <div class="navbar-collapse" style="background-color: #0f6cb2;">
                                <ul class="main-navigation">
                                    <li><a href="index.html" style="color: white" class="parent" id="home-nav">TRANG CHỦ</a> </li>
                                    <li style="position: relative">
                                    <a href="category.html" style="color: white" class="parent category-item" id="category-man">
                                     NAM
                                     <i class="fas fa-caret-down"></i> 
                                     </a>
                                    
                                    <ul class="man-category" style="background-color: #0f6cb2; border:none; position:absolute; top: 35px;left:-15px;">
                                    <li id="hitops-man" class="category-product category-product-man" style="color: white">HiTops</li>
                                    <li id="running-man"  class="category-product category-product-man" style="color: white">Running</li>
                                    <li id="slides-man"  class="category-product category-product-man" style="color: white">Slides</li>
                                    <li id="sneakers-man"  class="category-product category-product-man" style="color: white">Sneakers</li>
                                    <li id="skateboard-man"  class="category-product category-product-man" style="color: white">SkateBoard</li>
                                    <li id="football-man"  class="category-product category-product-man" style="color: white">Football</li>

                                </ul>
                            
                                </li>
                                    <li style="position: relative"><a href="category.html" style="color: white" class="parent category-item" id="category-woman">NỮ <i class="fas fa-caret-down"></i></a>
                                    
                                    <ul class="woman-category" style="background-color: #0f6cb2; border:none; position:absolute; top: 35px;left: -15px">
                                    <li id="hitops-woman" class="category-product category-product-woman" style="color: white">HiTops</li>
                                    <li id="running-woman"  class="category-product category-product-woman" style="color: white">Running</li>
                                    <li id="slides-woman"  class="category-product category-product-woman" style="color: white">Slides</li>
                                    <li id="sneakers-woman"  class="category-product category-product-woman" style="color: white">Sneakers</li>
                                    <li id="skateboard-woman"  class="category-product category-product-woman"style="color: white" >SkateBoard</li>
                                    <li id="football-woman"  class="category-product category-product-woman" style="color: white">Football</li>

                                </ul>

                                    </li>
                                     <li><a href="category.html" style="color: white" class="parent category-item" id="category-brand">NHÃN HÀNG <i class="fas fa-caret-down"></i></a></li>
                                    <li><a href="blog.html" style="color: white" class="parent" id="blog-nav">BLOG</a></li>
                                    <li><a href="about-us.html" style="color: white"  id="about-nav" >VỀ CHÚNG TÔI</a></li>
                                </ul>
                                <div class="nav-detail-info">
                
    
                                    <div class="item-nav-detail-info" id="item-category-brand">
                                        <div class="contain-brand" >
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-01.svg">
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-02.svg">
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-03.svg">
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-04.svg">
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-05.svg">
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-06.svg">
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-07.svg">
                                            <img onclick="location.href='category.html'" src="image/brand/logo-brand-08.svg">
                                        </div>
                                   </div>
        
                             </div>
                            </div>
                         </div>`;
    $("#menu-navigation").append(content);
};

$(displayMenuNavigation);


$(function () {
    let curPage = window.location.pathname;
    // return page
    if (curPage.includes("index")) {
        localStorage.setItem("nav-current", "home")
    }

    if (curPage.includes("blog")) {
        localStorage.setItem("nav-current", "blog")
    }

    if (curPage.includes("category")) {
        if (localStorage.getItem("currentPageCategory") === "man")
            localStorage.setItem("nav-current", "category-man")
        else
            localStorage.setItem("nav-current", "category-woman")

    }

    if (curPage.includes("about")) {
        localStorage.setItem("nav-current", "about")
    }

    // choose page
    $("#home-nav").click(() => {
        localStorage.setItem("nav-current", "home")
    })
    $("#category-man").click(() => {
        localStorage.setItem("nav-current", "category-man")
        $("#category-man").addClass("active-nav")
    })
    $("#category-woman").click(() => {
        localStorage.setItem("nav-current", "category-woman")
        $("#category-man").addClass("active-nav")
    })
    $("#blog-nav").click(() => {
        localStorage.setItem("nav-current", "blog")
        $("#blog-nav").addClass("active-nav")
    })
    $("#about-nav").click(() => {
        localStorage.setItem("nav-current", "about")
        $("#about-nav").addClass("active-nav")
    })
})

$(function () {
    if (localStorage.getItem("nav-current") === "home") {
        $("#home-nav").addClass("active-nav")
    }

    if (localStorage.getItem("nav-current") === "category-man") {
        $("#category-man").addClass("active-nav")
    }
    if (localStorage.getItem("nav-current") === "category-woman") {
        $("#category-woman").addClass("active-nav")
    }
    if (localStorage.getItem("nav-current") === "blog") {
        $("#blog-nav").addClass("active-nav")
    }
    if (localStorage.getItem("nav-current") === "about") {
        $("#about-nav").addClass("active-nav")
    }
})

// click price search in filter search
$(function () {
    $("#price-search-01").click(() => {
        $("#price-search-01").css("background-color", "#5f2525");
    });
    $("#price-search-02").click(() => {
        $("#price-search-02").css("background-color", "#5f2525");
    });
    $("#price-search-03").click(() => {
        $("#price-search-03").css("background-color", "#5f2525");
    });
});


// responsive navigation
$(function () {
    $("#category-man").mouseover(() => {
        setUpCategoryNav();
        $('.man-category').addClass('display-sub-nav');

    });
    $("#category-woman").mouseover(() => {
        setUpCategoryNav();
        $('.woman-category').addClass('display-sub-nav');
    });

    $("#category-brand").mouseover(() => {
        setUpCategoryNav();
        $("#item-category-brand").css("display", "flex");
        if ($(window).width() < 750) {
            $(".nav-detail-info").css("top", "120px");
            $(".nav-detail-info").css("left", "0px");
            $("#item-category-brand").css("width", "100%");
        }
    });

    $(".man-category").mouseover(() => {
        $('.man-category').addClass('display-sub-nav');
    });

    $(".woman-category").mouseover(() => {
        $('.woman-category').addClass('display-sub-nav');
    });

    $(".nav-detail-info").mouseover(() => {
        $(".nav-detail-info").css("display", "grid");
    });

    $(".navbar-collapse").mouseout(() => {
        $('.man-category').removeClass('display-sub-nav');
        $('.woman-category').removeClass('display-sub-nav');
        $(".nav-detail-info").css("display", "none");

    });
});

function setUpCategoryNav() {
    $(".nav-detail-info").css("display", "grid");
    $("#item-category-woman").css("display", "none");
    $("#item-category-man").css("display", "none")
    $("#item-category-running").css("display", "none");
    $("#item-category-brand").css("display", "none");
}


let listProduct = [
    { brand: "Nike", name: "Air Force One 1", saleRate: "20", oldPrice: "1.200.000", newPrice: "1.000.000", linkImg: "image/product/product-01.jpg" },
    { brand: "Nike", name: "Phantom GT Elite Firm Ground Football Boots Juniors", saleRate: "25", oldPrice: "1.900.000", newPrice: "800.000", linkImg: "image/product/product-02.jpg" },
    { brand: "Nike", name: "Air Force One 1", saleRate: "20", oldPrice: "1.200.000", newPrice: "1.500.000", linkImg: "image/product/product-03.webp" },
    { brand: "Nike", name: "Air Force One 1", saleRate: "30", oldPrice: "2.200.000", newPrice: "1.900.000", linkImg: "image/product/product-04.webp" },
    { brand: "Nike", name: "Air Force One 1", saleRate: "15", oldPrice: "3.200.000", newPrice: "2.200.000", linkImg: "image/product/product-05.webp" },
    { brand: "Nike", name: "Air Force One 1", saleRate: "10", oldPrice: "2.200.000", newPrice: "1.800.000", linkImg: "image/product/product-06.jpg" },
    { brand: "Nike", name: "Air Force One 1", saleRate: "2", oldPrice: "3.200.000", newPrice: "1.200.000", linkImg: "image/product/product-07.webp" },
    { brand: "Nike", name: "Air Force One 1", saleRate: "12", oldPrice: "2.200.000", newPrice: "2.000.000", linkImg: "image/product/product-08.jpg" },
    { brand: "Nike", name: "Air Force One 1", saleRate: "15", oldPrice: "3.200.000", newPrice: "3.000.000", linkImg: "image/product/product-09.webp" },


]
function displayListProduct() {
    listProduct.forEach(element => {
        let contain = `<div class="product-layout product-grid col-lg-4 col-md-5 col-sm-6 col-xs-10" style="background-color: white; padding: 10px 0">
        <div class="product-thumb">
          <div class="image product-imageblock">
            <a href="product.html">
                <div class="related-product-sale">
                   <h5 style='margin-top: 10px'> -20%</h5>
                 </div>
<img id="imgCategory" src="${element.linkImg}" alt="lorem ippsum dolor dummy" title="lorem ippsum dolor dummy" class="img-responsive item-img-list" /> </a>
            <div class="button-group button-group-list-category">
            <button type="button" class="wishlist" data-toggle="tooltip" title="Add to Wish List"><i class="fas fa-heart"></i></button>
            <button type="button" class="addtocart-btn">Mua Ngay</button>
            <button type="button" class="compare" data-toggle="tooltip" title="Compare this Product"><i class="fas fa-shopping-cart"></i></button>            </div>
          </div>
          <div class="caption product-detail intro-product-detail-list">
         <h2 id="brandCategory" style='margin-top: 5px; text-transform: uppercase'>${element.brand}</h2>
            <h4 class="product-name">
<a id="nameCategory" href="product.html" title="lorem ippsum dolor dummy" style='text-transform: capitalize'>
${element.name}</a> </h4> 
            <p class="product-desc"> More room to move. With 80GB or 160GB of storage and up to 40 hours of battery life, the new lorem ippsum dolor dummy lets you enjoy up to 40,000 songs or up to 200 hours of video or any combination wherever you go. Cover
              Flow. Browse through your music collection by flipping..</p> 
            <p id="priceCategory" class="price product-price produce-price-list">
            <span class="price-old" style='margin-right: 10px'>${element.oldPrice} VND</span>
            ${element.newPrice} VND </p>
            <div class="rating"> <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span> <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span>                                    <span class="fa fa-stack"><i class="fa fa-star fa-stack-2x"></i><i class="fa fa-star-o fa-stack-2x"></i></span> <span class="fa fa-stack"><i class="fa fa-star-o fa-stack-2x"></i></span> <span class="fa fa-stack"><i class="fa fa-star-o fa-stack-2x"></i></span>                                    </div>
          </div>
          <div class="button-group button-group-list-category">
          <button type="button" class="wishlist" data-toggle="tooltip" title="Add to Wish List"><i class="fas fa-heart"></i></button>
          <button type="button" class="addtocart-btn">Mua Ngay</button>
          <button type="button" class="compare" data-toggle="tooltip" title="Compare this Product"><i class="fas fa-shopping-cart"></i></button>          </div>
        </div>
      </div>`;
        $(".grid-list-wrapper").append(contain);
    });


}






/* display listProduct by click navigation*/
$(function () {
    let man = $(".category-product");
    for (let i = 0; i < man.length; i++) {
        man[i].addEventListener("click", (e) => {
            $(".contain-carousel-category").css("display", "none");
            displayListProduct();
            displayPagination()
            return;
        });
    }
})

// to set category man or category woman
$(function () {
    $("#category-man").click(() => {
        localStorage.setItem("currentPageCategory", "man");
        localStorage.setItem("categoryByNav", "");
        $(".contain-carousel-category").css("display", "block");
        $(".grid-list-wrapper").empty();
    });
    $("#category-woman").click(() => {
        localStorage.setItem("currentPageCategory", "woman");
        localStorage.setItem("categoryByNav", "");
        $(".contain-carousel-category").css("display", "block");
        $(".grid-list-wrapper").empty();
    });
});

let listIntroCategoryMan = [];
listIntroCategoryMan.push("data/product/Shoe/HiTops/HiTopsMan.js");
listIntroCategoryMan.push("data/product/Shoe/Running/RunningMan.js");
listIntroCategoryMan.push("data/product/Shoe/Slides/SlidesMan.js");
listIntroCategoryMan.push("data/product/Shoe/Sneakers/SneakersMan.js");
listIntroCategoryMan.push("data/product/Shoe/SkateBoard/SkateBoardMan.js");
listIntroCategoryMan.push("data/product/Shoe/Football/FootballMan.js");
let listIntroCategoryWoman = [];
listIntroCategoryWoman.push("data/product/Shoe/HiTops/HiTopsWoman.js");
listIntroCategoryWoman.push("data/product/Shoe/Running/RunningWoman.js");
listIntroCategoryWoman.push("data/product/Shoe/Slides/SlidesWoman.js");
listIntroCategoryWoman.push("data/product/Shoe/Sneakers/SneakersWoman.js");
listIntroCategoryWoman.push("data/product/Shoe/SkateBoard/SkateBoardWoman.js");
listIntroCategoryWoman.push("data/product/Shoe/Football/FootballWoman.js");




function loadImageCarouselAndIntroduceCategory() {
    if (localStorage.getItem("currentPageCategory") == "man") {
        $("#category-carousel-intro-1").attr("src", "image/banners/men-intro-carousel1.webp");
        $("#category-carousel-intro-2").attr("src", "image/banners/men-intro-carousel2.webp");
        $("#category-carousel-intro-3").attr("src", "image/banners/men-intro-carousel-3.jpg");
        $("#category-intro-img-hitops").attr("src", "image/category/man-Hitops.webp");
        $("#category-intro-img-running").attr("src", "image/category/man-running.webp");
        $("#category-intro-img-slides").attr("src", "image/category/man-slides.jpg");
        $("#category-intro-img-football").attr("src", "image/category/man-football.webp");
        $("#category-intro-img-sneakers").attr("src", "image/category/man-sneakers.jpg");
        $("#category-intro-img-skateboard").attr("src", "image/category/man-skateboard.jpg");
    }
    else {
        $("#category-carousel-intro-1").attr("src", "image/banners/women-intro-carousel1.webp");
        $("#category-carousel-intro-2").attr("src", "image/banners/women-intro-carousel2.webp");
        $("#category-carousel-intro-3").attr("src", "image/banners/women-intro-carousel3.webp");
        $("#category-intro-img-hitops").attr("src", "image/category/woman-hitops.jpg");
        $("#category-intro-img-running").attr("src", "image/category/running-woman.webp");
        $("#category-intro-img-slides").attr("src", "image/category/woman-slide.jpg");
        $("#category-intro-img-football").attr("src", "image/category/woman-football.jfif");
        $("#category-intro-img-sneakers").attr("src", "image/category/woman-sneakers.webp");
        $("#category-intro-img-skateboard").attr("src", "image/category/woman-skateboard.jfif");
    }
}
$(window).ready(loadImageCarouselAndIntroduceCategory);



function displayPagination() {
    let contain = "<span>Page  </span><select id=\"selectPagination\">";
    for (let i = 1; i <= 9; i++)
        contain += `<option value="${i}">${i}</option>`
    contain += `</select> <span> of 9 </span>`;
    $(".pagination").append(`<li><a href="#" id="left-page">&lt;</a></li><li><a href="#" id="right-page">&gt;</a></li>`);
    $(".pagination").append(contain);
    $(`#selectPagination option[value=1]`).attr('selected', 'selected');
};



function displaySizeShoe() {
    let sizeArr = [34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45]
    sizeArr.forEach((value) => {
        $(".contain-dropdown-size").append(
            " <label class=\"checkbox-filter-search\">\n" +
            " <input name=\"filter[]\" type=\"checkbox\" value=\"1\" />\n" +
            value + "</label>"
        );
    });
}

$(displaySizeShoe)


// access information of page costumer
function accessInfoCustomer() {
    $("#nav-info-customer").click(() => {
        resetAllSectionCustomer();
        $("#info-customer").css("display", "block");
        $("#nav-info-customer").addClass("customer-category-after-click");
        $("#info-customer").addClass("display-default-customer")
    });

    $("#nav-favorite-customer").click(() => {
        resetAllSectionCustomer();
        $("#favorite-customer").css("display", "flex");
        $("#nav-favorite-customer").addClass("customer-category-after-click");
        removeDefaultNavContentCategory()
    });

    $("#nav-bank-customer").click(() => {
        resetAllSectionCustomer();
        $("#bank-customer").css("display", "block");
        $("#nav-bank-customer").addClass("customer-category-after-click");
        removeDefaultNavContentCategory()
    });
    $("#nav-map-customer").click(() => {
        resetAllSectionCustomer();
        $("#map-customer").css("display", "block");
        $("#nav-map-customer").addClass("customer-category-after-click");
        removeDefaultNavContentCategory()
    });
    $("#nav-password-customer").click(() => {
        resetAllSectionCustomer();
        $("#password-customer").css("display", "block");
        $("#nav-password-customer").addClass("customer-category-after-click");
        removeDefaultNavContentCategory()
    });

    $("#nav-history-customer").click(() => {
        resetAllSectionCustomer();
        $("#history-customer").css("display", "block");
        $("#nav-history-customer").addClass("customer-category-after-click");
        $("#info-customer").css("display", "none");
    });

    $(".adjust-address-customer").click(() => {
        $(".contain-form-adjust-address").css("display", "block");
        $(".default-address").css("display", "none");
        $(".available-address").css("display", "none");
        $("#submit-adjust-address-customer").val("Chỉnh sửa");
    });

    $(".contain-add-map").click(() => {
        $(".contain-form-adjust-address").css("display", "block");
        $(".available-address").css("display", "none");
        $("#submit-adjust-address-customer").val("Thêm");
        $(".default-address").css("display", "none");
    });

    $("#submit-adjust-address-customer").click(() => {
        $(".contain-form-adjust-address").css("display", "none");
        $(".available-address").css("display", "block");
        $(".default-address").css("display", "block");
    });
    $("#delete-adjust-address-customer").click(() => {
        $(".contain-form-adjust-address").css("display", "none");
        $(".available-address").css("display", "block");
        $(".default-address").css("display", "block");
    });
}

function resetAllSectionCustomer() {
    $("#nav-bank-customer").removeClass("customer-category-after-click");
    $("#nav-map-customer").removeClass("customer-category-after-click");
    $("#nav-password-customer").removeClass("customer-category-after-click");
    $("#nav-favorite-customer").removeClass("customer-category-after-click");
    $("#nav-history-customer").removeClass("customer-category-after-click");
    $("#bank-customer").css("display", "none");
    $("#map-customer").css("display", "none");
    $("#password-customer").css("display", "none");
    $("#history-customer").css("display", "none");
    $("#favorite-customer").css("display", "none")

}
$(window).ready(resetAllSectionCustomer);

function removeDefaultNavContentCategory() {
    $("#nav-info-customer").removeClass("customer-category-after-click");
    $("#info-customer").css("display", "none");
}
$(window).ready(accessInfoCustomer);

// load location customer
$(function () {
    loadDefaultLocation();
    $('#contain-option-city').on('change', function () {
        let cityCode = $(this).val();
        loadDistrict(cityCode);
        let codeFirstDistrict = "";
        for (let i = 0; i < districts.length; i++) {
            if (districts[i].parent_code == cityCode) {
                codeFirstDistrict = districts[i].code;
                break;
            }
        }
        loadWard(codeFirstDistrict);
    });

    $('#contain-option-district').on('change', function (e) {
        let districtCode = $(this).val();
        loadWard(districtCode);
    });
});

function loadDefaultLocation() {
    loadCity();
    loadDistrict(89,);
    loadWard(883);
}

function loadCity() {
    let location = new XMLHttpRequest();
    location.open("GET", "data/location/city.js");
    location.send();
    location.onload = function () {
        let cities = JSON.parse(location.responseText);
        let city = "";
        cities.forEach((element) => {
            city += "<option value=\"" + element.code + "\"" + "class=\"city\">" + element.name + "</option>\n";
        });
        $("#contain-option-city").append(city);
    }
}

function loadDistrict(districtCode) {
    let location = new XMLHttpRequest();
    location.open("GET", "data/location/district.js");
    location.send();
    location.onload = function () {
        let data = JSON.parse(location.responseText);
        districtArr = data.filter((value) => value.parent_code == districtCode);
        let district = "";
        districtArr.forEach((element) => {
            district += "<option value=\"" + element.code + "\"" + "class=\"district\">" + element.name + "</option>\n";
        });
        $("#contain-option-district").text("");
        $("#contain-option-district").append(district)
    }
}

function loadWard(wardCode) {
    let location = new XMLHttpRequest();
    location.open("GET", "data/location/ward.js");
    location.send();
    location.onload = function () {
        let data = JSON.parse(location.responseText);
        let wardArr = data.filter((value) => value.parent_code == wardCode);
        let ward = "";
        wardArr.forEach((element) => {
            ward += "<option value=\"" + element.code + "\"" + "class=\"ward\">" + element.name + "</option>\n";
        });
        $("#contain-option-ward").text("");
        $("#contain-option-ward").append(ward);
    }
}

function test123(wardCode) {

    let location = new XMLHttpRequest();
    location.open("GET", "data/product.json");
    location.send();
    location.onload = function () {
        let data = JSON.parse(location.responseText);
        let re = ""
        data.forEach((e) => {
            let locations = new XMLHttpRequest();
            locations.open("GET", "data/LinkImg.js");
            locations.send();
            locations.onload = function () {
                let re = "";
                let datas = JSON.parse(locations.responseText);
                let res = 0
               datas.forEach((element) => {
                    if (element.id == e.id) {
                        if (element.img.slice(-1)=="a")
                        res++
                    }
                });
                if (res == 1) {
                    re += blabla(e, "white")
                }
                else if (res == 2) {
                    re += blabla(e, "white")
                    re += blabla(e, "black")
                }
                else if (res == 3) {
                    re += blabla(e, "white")
                    re += blabla(e, "black")
                    re += blabla(e, "blue")
                }
                else if (res == 4) {
                    re += blabla(e, "white")
                    re += blabla(e, "black")
                    re += blabla(e, "blue")
                    re += blabla(e, "green")
                }
                else if (res > 4) {
                    re += blabla(e, "white")
                    re += blabla(e, "black")
                    re += blabla(e, "blue")
                    re += blabla(e, "green")
                    re += blabla(e, "yellow")

                }
                console.log(re)
            }
  });

        console.log(re)

    }
}

$(test123)

function blabla(e, color) {
    let size = [35, 36, 37, 38, 39, 40, 41, 42, 43]
    let input = [47, 56, 52, 63, 72];
    let sole = [10, 34, 30, 25, 15];
    let r = Math.random() * (size.length - 3) + 3;
    let tmpArrSize = []
    let re = "";

    for (let i = 0; i < r; i++) {
        let sizeTmp = size[Math.floor(Math.random() * size.length)]
        if (tmpArrSize.includes(size)) {
            i--
            continue
        }
        tmpArrSize.push(sizeTmp)
        re += "{"
        re += `"id":` + `"${e.id}",\n`;
        re += `"size":` + `"${sizeTmp}",\n`
        re += `"totalValue":` + `"${input[Math.floor(Math.random() * input.length)]}",\n`
        re += `"soleValue":` + `"${sole[Math.floor(Math.random() * sole.length)]}",\n`
        re += `"createAt":` + `"${Math.floor(Math.random() * 27) + 1}/${Math.floor(Math.random() * 11) + 1}/2020",\n`
        re += `"updateAt":` + `"${Math.floor(Math.random() * 27) + 1}/${Math.floor(Math.random() * 11) + 1}/2021",\n`
        re += `"active":` + 1 + ",\n"
        re += `"color":` + `"${color}"`
        re += "},\n"
        re += ""
    }
    return re
}


