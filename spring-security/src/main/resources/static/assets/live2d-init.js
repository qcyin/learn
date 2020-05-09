/**
 * <script src="https://eqcn.ajz.miesnfu.com/wp-content/plugins/wp-3d-pony/live2dw/lib/L2Dwidget.min.js"></script>
*/

L2Dwidget.init({
    "model": {
        jsonPath: "https://unpkg.com/live2d-widget-model-shizuku@1.0.5/assets/shizuku.model.json",
        "scale": 1
    },
    "display": {
        "position": "right",
        "width": 150,
        "height": 300,
        "hOffset": 0,
        "vOffset": -20
    },
    "mobile": {
        "show": true,
        "scale": 0.5
    },
    "react": {
        "opacityDefault": 0.7,
        "opacityOnHover": 0.2
    }
});