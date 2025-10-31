<template>
  <el-container>
    <el-main>
      <div class="centered-tabs-container">
        <el-tabs v-model="activeTab" stretch>
          <!-- 成果轮播图 -->
          <el-tab-pane label="成果轮播图" name="achievements">
            <div id="achievements-content" class="tab-content scrollable">
              <el-button type="primary" @click="triggerFileInput">添加图片</el-button>

              <div
                v-if="!filteredAchievementsImages.length && activeTab === 'achievements'"
                class="no-data"
              >
                暂无图片，请添加。
              </div>

              <div v-if="filteredAchievementsImages.length > 0" class="waterfall-gallery">
                <div class="cell-item" v-for="(item, index) in filteredAchievementsImages" :key="index">
                  <el-card shadow="hover">
                    <!-- 预览从点击项开始（方案B：按URL计算索引） -->
                    <el-image
                      class="image"
                      :src="normalizeUrl(item.photo)"
                      :preview-src-list="achievementsPreviewList"
                      :initial-index="achievementsIndexOf(item.photo)"
                      fit="cover"
                      preview-teleported
                    />
                    <div class="image-actions">
                      <el-button type="danger" size="small" @click="deleteImage(item.photo)">删除</el-button>
                    </div>
                  </el-card>
                </div>
              </div>
            </div>
          </el-tab-pane>

          <!-- 首页轮播图 -->
          <el-tab-pane label="首页轮播图" name="home">
            <div id="home-content" class="tab-content scrollable">
              <el-button type="primary" @click="triggerFileInput">添加图片</el-button>

              <div v-if="!filteredHomeImages.length && activeTab === 'home'" class="no-data">
                暂无图片，请添加。
              </div>

              <div v-if="filteredHomeImages.length > 0" class="waterfall-gallery">
                <div class="cell-item" v-for="(item, index) in filteredHomeImages" :key="index">
                  <el-card shadow="hover">
                    <!-- 预览从点击项开始（方案B：按URL计算索引） -->
                    <el-image
                      class="image"
                      :src="normalizeUrl(item.photo)"
                      :preview-src-list="homePreviewList"
                      :initial-index="homeIndexOf(item.photo)"
                      fit="cover"
                      preview-teleported
                    />
                    <div class="image-actions">
                      <el-button type="danger" size="small" @click="deleteImage(item.photo)">删除</el-button>
                    </div>
                  </el-card>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>

        <!-- 全局隐藏文件选择（两个 tab 都可用） -->
        <input type="file" ref="fileInput" @change="handleFileUpload" style="display: none;" />

        <el-backtop target=".scrollable" :visibility-height="20" :bottom="75">
          <div
            style="
              display: flex;
              align-items: center;
              justify-content: center;
              height: 100%;
              background-color: #1989fa;
              border-radius: 50%;
              box-shadow: rgb(16 0 0 / 41%) 0px 12px 6px 0px;
              color: #fff;
              width: 100%;
            "
          >
            <el-icon><Top /></el-icon>
          </div>
        </el-backtop>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { ref, onMounted, computed, watch } from 'vue';
import axios from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';

export default {
  name: 'LbtManagement',
  setup() {
    const homeImages = ref([]);          // 首页轮播图
    const achievementsImages = ref([]);  // 成果轮播图
    const activeTab = ref('home');       // 当前激活的标签页
    const fileInput = ref(null);         // 文件输入框引用
    const token = 'your-token-here';     // TODO: 替换为真实 token

    // URL 归一化（反斜杠 -> 正斜杠）
    const normalizeUrl = (u) => {
      if (!u) return '';
      const s = Array.isArray(u) ? u[0] : u;
      return String(s).replace(/\\/g, '/');
    };

    // 拉取数据
    const fetchData = async (tab) => {
      try {
        const url = tab === 'home' ? '/lbt/getHomeLbt' : '/lbt/getCGLbt';
        const { data } = await axios.get(url);
        if (tab === 'home') {
          homeImages.value = data?.data || [];
        } else {
          achievementsImages.value = data?.data || [];
        }
      } catch (error) {
        console.error('Error fetching data:', error);
        ElMessage.error('加载失败');
      }
    };

    watch(activeTab, (newTab) => {
      fetchData(newTab);
    });

    // 过滤有效图片
    const filteredHomeImages = computed(() =>
      homeImages.value.filter((item) => item && item.photo)
    );
    const filteredAchievementsImages = computed(() =>
      achievementsImages.value.filter((item) => item && item.photo)
    );

    // 预览列表
    const homePreviewList = computed(() =>
      filteredHomeImages.value.map((i) => normalizeUrl(i.photo)).filter(Boolean)
    );
    const achievementsPreviewList = computed(() =>
      filteredAchievementsImages.value.map((i) => normalizeUrl(i.photo)).filter(Boolean)
    );

    // 按 URL 计算在预览列表中的真实索引（方案B）
    const makeIndexOf = (listRef) => (url) =>
      (listRef.value || []).indexOf(normalizeUrl(url));

    const homeIndexOf = makeIndexOf(homePreviewList);
    const achievementsIndexOf = makeIndexOf(achievementsPreviewList);

    // 选择文件
    const triggerFileInput = () => fileInput.value?.click();

    // 上传文件
    const handleFileUpload = async () => {
      const file = fileInput.value ? fileInput.value.files[0] : null;
      if (!file) return;

      const formData = new FormData();
      formData.append('image', file);

      try {
        const url = activeTab.value === 'home' ? '/lbt/addHome' : '/lbt/addCG';
        await axios.post(url, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            token
          }
        });
        ElMessage.success('上传成功');
        await fetchData(activeTab.value);
      } catch (error) {
        console.error('Error uploading image:', error);
        ElMessage.error('上传图片时出错');
      } finally {
        if (fileInput.value) fileInput.value.value = '';
      }
    };

    // 删除（带二次确认）
    const deleteImage = async (url) => {
      if (!url) return;
      try {
        await ElMessageBox.confirm(
          '确定删除该图片吗？删除后不可恢复。',
          '删除确认',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
            autofocus: false,
            closeOnClickModal: false,
            closeOnPressEscape: true
          }
        );
        await axios.delete(`/lbt/deleteLbt?url=${encodeURIComponent(url)}`, {
          headers: { token }
        });
        ElMessage.success('删除成功');
        await fetchData(activeTab.value);
      } catch (error) {
        if (error === 'cancel' || error === 'close') {
          ElMessage.info('已取消删除');
        } else {
          console.error('Error deleting image:', error);
          ElMessage.error('删除图片时出错');
        }
      }
    };

    onMounted(() => {
      fetchData(activeTab.value);
    });

    return {
      homeImages,
      achievementsImages,
      activeTab,
      fileInput,
      triggerFileInput,
      handleFileUpload,
      deleteImage,

      normalizeUrl,
      filteredHomeImages,
      filteredAchievementsImages,
      homePreviewList,
      achievementsPreviewList,
      homeIndexOf,
      achievementsIndexOf,
      fetchData
    };
  }
};
</script>

<style scoped>
.centered-tabs-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  height: calc(85vh - 40px);
}

.el-tabs {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.tab-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  height: calc(85vh - 100px);
  overflow-y: auto;
  width: 100%;
}

.waterfall-gallery {
  margin-top: 20px;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.cell-item {
  width: calc(33% - 20px);
}

.image {
  width: 100%;
  height: auto; /* 如需固定高可改为 height: 200px; 并配合 fit="cover" */
  border-radius: 8px;
}

.image-actions {
  text-align: center;
  margin-top: 10px;
}

.no-data {
  margin-top: 20px;
  color: #AAAAAA;
  font-size: 16px;
}
</style>
