<template>
  <div>
    <HeaderComponent></HeaderComponent>
    <div>
      <div class="reportview-reportlist-title">
        <div class="reportview-reportlist-title-text">
        낭만보고서
        </div>
        <hr class="reportview-reportlist-title-line">
      </div>
      <div v-if="!reportsData.reportList[0]"><img src="../../assets/nothing-reports.png" alt="낭만보고서가 없어요..." style="display: block; margin: 0 auto; width: 80%; height: 80%;"><p style="text-align: center; font-size: 20px; margin-bottom: 100px">낭만보고서가 없어요...<br>먼저 낭만버스를 이용해주세요!</p></div>
      <div v-if="reportsData.reportList[0]"><!-- 리포트데이터가 있으면 보여줌 -->
        <div v-for="report in reportsData.reportList" :key="report.time"
          class="reportview-content"
        ><!-- 시간 순서로 리포트아이템 출력 -->
          <router-link :to="{ name: 'reportsdetail', params: { reportsId: report.id }}" class="reportview-content-router">
            <div class="reportview-busnum-date">
              <div class="reportview-data-time">
                <div class="reportview-date">
                  {{ parseInt(report.createDay.substr(5, 2))>9 ? report.createDay.substr(5, 2) : parseInt(report.createDay.substr(5, 2))%10 }}월
                  {{ report.createDay.substr(8, 2) }}일
                </div>
                <div class="reportview-time">
                {{ report.createTime.substr(0, 2) }}시
                {{ report.createTime.substr(3, 2) }}분<!-- 시간 출력 -->
                </div>
              </div>
              <div class="reportview-busnum">
                {{ report.routeNo }}번<!-- 버스 번호 -->
              </div>
            </div>
            <div class="reportview-comment">
              "{{ report.content.substr(0, 18) }}..."<!-- 가장 좋아요를 많이 받은 채팅(12자만 출력) -->
            </div>
          </router-link>
        </div>
      </div>
    </div>
    <FooterComponent></FooterComponent>
  </div>
</template>
<script setup>
  import { ref, onMounted } from 'vue'
  import HeaderComponent from '../components/HeaderComponent.vue'
  import FooterComponent from '../components/FooterComponent.vue'
  import axios from 'axios'
  import api from '../../api/api.js'
  import { useStore } from 'vuex'

  const reportsData = ref({
    reportList: []
  })

  const userId = localStorage.accountUserId

  onMounted(() => {
    reportListGetting(userId)
  })

  const store = useStore()
 
  // 리포트 목록 가져오기
  function reportListGetting(userId) {
    axios({
      url: api.reports.reportsList(userId),
      method: 'get',
    })
      .then(res => {
        reportsData.value.reportList = res.data
        // 특히 보고서번호랑 버스번호는 매칭시켜
        // 보고서번호마다 state넣기
        for (let i=0; i<reportsData.value.reportList.length; i++) {
          // console.log(reportsData.value.reportList[i])
          store.commit("mainPage/SET_BUS_NUM_SAVE", { 
            reportId: reportsData.value.reportList[i].id,
            busNum: reportsData.value.reportList[i].routeNo
            } )
          
        }
      })
      .catch(err => {
        console.error(err, '낭만보고서가 없거나 가져오는 데 실패했어요...')
      })
  }
  // 더미데이터
  // reportsData.value.reportList[0] = {
  //   id: 1,
  //   createDay: '2022-08-12',
  //   createTime: '16:16:00',
  //   routeNo: '3414',
  //   content: '심리적 의존 관계, 의존 상태를 벗어나야 합니다. 국민들이 "내 나라는 내가 지킨다"라고 하는 의지와 자신감을 가지고 있어야 국방이 되는 것이지...',
  // }
  // reportsData.value.reportList[1] = {
  //   id: 2,
  //   createDay: '2022-08-13T16:32:00',
  //   createTime: '16:32',
  //   routeNo: '3414',
  //   content: '또 다시 혼자가 되는게 두려워 외면했었네',
  // }
  

</script>

<style>
.reportview-reportlist-title {
  display: flex;
  align-items: center;
  margin: 101.75px 0 45px 32px;
}

.reportview-reportlist-title-text {
  font-size: 1.5rem;
  font-family: BM HANNA Pro;
}

.reportview-reportlist-title-line {
  width: 55%;
  height: 1px;
  border: 0;
  border-top: 1px solid black;
}
/* 리포트 미리보기 */
.reportview-content {
  color: black;
  height: 85px;
  padding: 16px;
  margin: 32px;
  background: #F5F5F5;
  border-radius: 6px;
}
.reportview-content-router {
  display: flex;
  align-items: center;
}
.reportview-busnum-date {
  display: flex;
  flex-direction: column;
  width: 200px;
  margin-right: 20px;
}
.reportview-date {
  color: black;
  font-size: 15px;
  line-height: 150%;
}
.reportview-time {
  color: black;
  font-size: 15px;
  line-height: 150%;
  margin-bottom: 5px;
}
.reportview-busnum {
  color: #F34949;
  font-family: BM HANNA Pro;
  font-size: 35px;
}
.reportview-comment {
  color: black;
  font-size: 1rem;
  line-height: 35px;
  font-style: italic;
}
</style>
