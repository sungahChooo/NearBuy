document.addEventListener("DOMContentLoaded", function () {
  const menuItems = document.querySelectorAll(".menu-item");
  const subMenus = document.querySelectorAll(".submenu");

  menuItems.forEach(item => {
    item.addEventListener("click", function () {
      menuItems.forEach(item => item.classList.remove("active"));
      subMenus.forEach(subMenu => subMenu.style.display = "none");
      
      this.classList.add("active");
      
      if (this.id === "sellList" /*|| this.id === "wishList"*/) {
        document.querySelector("#subList").style.display = "block";
        document.querySelector("#subList").style.position = "relative"; // 추가된 부분
        document.querySelector("#subList").style.top = "0"; // 추가된 부분
        document.querySelector("#sale").style.display = "block";
        document.querySelector("#soldOut").style.display = "block";
      } else {
        document.querySelector("#subList").style.display = "none";
      }
    });
  });
});
  const sellList = document.querySelector('#sellList');

  sellList.addEventListener('click', function() {
    this.classList.toggle('active'); // 클래스 토글
    wishList.classList.remove('active'); // 다른 목록은 비활성화
  });

/*  const wishList = document.querySelector('#wishList');
  wishList.addEventListener('click', function() {
    this.classList.toggle('active');
    sellList.classList.remove('active');
  });
*/
//---------------------------------------------------------------
    function showAll() {
        var productCards = document.querySelectorAll(".product-card");
        productCards.forEach(card => {
            card.style.display = "block";
        });
    }

    function showSelling() {
        var productCards = document.querySelectorAll(".product-card");
        productCards.forEach(card => {
            var productInfo = card.querySelector(".product-info");
            if (productInfo) {
                var state = productInfo.getAttribute("data-state");
                if (state === "판매중") {
                    card.style.display = "block";
                } else {
                    card.style.display = "none";
                }
            }
        });
    }

    function showSold() {
        var productCards = document.querySelectorAll(".product-card");
        productCards.forEach(card => {
            var productInfo = card.querySelector(".product-info");
            if (productInfo) {
                var state = productInfo.getAttribute("data-state");
                if (state === "판매완료") {
                    card.style.display = "block";
                } else {
                    card.style.display = "none";
                }
            }
        });
    }








