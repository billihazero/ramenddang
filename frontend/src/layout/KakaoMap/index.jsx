import React, { useEffect, useState } from 'react';
import {
  CustomOverlayMap,
  Map,
  MapMarker,
  MarkerClusterer,
} from 'react-kakao-maps-sdk';
import { clusterPositionData } from '../../data/clusterPositionData';

const KAKAO_ADDR = '경기 성남시 분당구 판교역로 166';

const KakaoMap = () => {
  const [map, setMap] = useState(null);
  const [positions, setPositions] = useState([]);

  useEffect(() => {
    setPositions(clusterPositionData.positions);
  }, []);

  useEffect(() => {
    if (!map || !window.kakao?.maps?.services) return;

    const kakao = window.kakao;
    const geocoder = new kakao.maps.services.Geocoder();

    geocoder.addressSearch(KAKAO_ADDR, (result, status) => {
      if (status !== kakao.maps.services.Status.OK || !result?.length) {
        // 실패 시 기본 위치 유지
        return;
      }
      const { x, y } = result[0]; // x=lng, y=lat
      const center = new kakao.maps.LatLng(parseFloat(y), parseFloat(x));

      // 지오코딩 직후 살짝 확대해서 보기 좋게
      map.setLevel(5);
      map.setCenter(center);

      // 시작 위치 마커 하나
      new kakao.maps.Marker({ position: center }).setMap(map);
    });
  }, [map]);

  const onClusterclick = (_target, cluster) => {
    // 현재 지도 레벨에서 1레벨 확대한 레벨
    const level = map.getLevel() - 1;

    // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
    map.setLevel(level, { anchor: cluster.getCenter() });
  };
  return (
    <>
      <Map // 지도를 표시할 Container
        center={{
          // 지도의 중심좌표
          lat: 36.2683,
          lng: 127.6358,
        }}
        style={{
          // 지도의 크기
          width: '100%',
          height: '100%',
        }}
        level={5} // 지도의 확대 레벨
        onCreate={setMap}
      >
        <MarkerClusterer
          averageCenter={true} // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
          minLevel={10} // 클러스터 할 최소 지도 레벨
          disableClickZoom={true} // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
          // 마커 클러스터러에 클릭이벤트를 등록합니다
          // 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
          // 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
          onClusterclick={onClusterclick}
        >
          {positions.map((pos) => (
            <MapMarker
              key={`${pos.lat}-${pos.lng}`}
              position={{
                lat: pos.lat,
                lng: pos.lng,
              }}
            />
          ))}
        </MarkerClusterer>
      </Map>
    </>
  );
};

export default KakaoMap;
