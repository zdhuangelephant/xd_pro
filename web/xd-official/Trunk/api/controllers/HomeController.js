/**
 * HomeController
 */
module.exports = {
  index: function(req, res) {
    res.view('index');
  },
  products: function(req, res) {
    res.view('product/products');
  },
  xddt: function(req, res) {
    res.view('news/xddt');
  },
  services: function(req, res) {
    res.view('service/services');
  },
  joinUs: function(req, res) {
    res.view('join/joinUs');
  },
  aboutUs: function(req, res) {
    res.view('about/aboutUs');
  }
};

