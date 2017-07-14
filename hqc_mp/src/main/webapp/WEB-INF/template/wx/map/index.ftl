<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>东部华侨城.导航地图</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<link href="${base}/map/map.css" rel="stylesheet" type="text/css">
		<link href="${base}/statics/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		 [#include "/wx/common_js.ftl"]
		<script src="${base}/map/map.js"></script>
		<script src="${base}/statics/libs/jquery.min.js"></script>
		<script src="${base}/statics/libs/bootstrap.min.js"></script>
		<script src="${base}/statics/plugins/layer/mobile/layer.js"></script>
		 <script type="text/javascript" src="${base}/statics/js/wx/jweixin-1.0.0.js"></script>
		<link href="${base}/map/index.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	<div class="btn-group btn-group-justified" id="map_nav"></div>
	<div id="mapcontainer" style="height: 90%; position: relative;" class="leaflet-container leaflet-fade-anim" tabindex="0">
		<div class="leaflet-map-pane" style="transform: translate3d(-438px, 205px, 0px);">
			<div class="leaflet-tile-pane">
				<div class="leaflet-layer">
					<div class="leaflet-tile-container"></div>
					<div class="leaflet-tile-container leaflet-zoom-animated"></div>
				</div>
			</div>
			
		</div>
	</div>
	<div class="right-top">
      <a class="VR" href="#">
        <img src="${base}/map/images/360.png">
      </a>
       <a class="VR" style="margin-top:10px" href="#">
        <img src="${base}/map/images/mapRange.png">
      </a>
      100米
	</div>
	<div class="right-bottom">
		<div class="list-nav" id="list-nav">
			<div class="inner" onclick="show_model()">
			  <i></i>
			  <i></i>
			  <i></i>
			</div>
		  </div>
	</div>

	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body" id="modalContent">
					
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${base}/map/index.js"></script>
	</body>
</html>
