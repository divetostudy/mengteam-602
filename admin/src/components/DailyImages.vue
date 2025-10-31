<template>
  <el-container>
    <el-main>
      <div class="centered-tabs-container">
        <el-tabs v-model="activeTab" stretch>
          <el-tab-pane label="Food" name="food">
            <div id="food-content" class="tab-content scrollable">
              <el-button type="primary" @click="triggerFileInput">添加图片</el-button>
              <div v-if="!filteredFoodImages.length && activeTab === 'food'" class="no-data">暂无图片，请添加。</div>
              <div v-if="filteredFoodImages.length > 0" class="waterfall-gallery">
                <div class="cell-item" v-for="(item, index) in filteredFoodImages" :key="index">
                  <el-card shadow="hover">
                    <!-- 可预览大图（同组预览，按 URL 计算初始索引） -->
                    <el-image
                      class="image"
                      :src="normalizeUrl(item.photoUrl)"
                      :preview-src-list="foodPreviewList"
                      :initial-index="foodIndexOf(item.photoUrl)"
                      fit="cover"
                      preview-teleported
                    />
                    <div class="image-actions">
                      <el-button type="danger" size="small" @click="deleteImage(item.photoUrl)">删除</el-button>
                    </div>
                  </el-card>
                </div>
              </div>
              <input type="file" ref="fileInput" @change="handleFileUpload" style="display: none;" />
            </div>
          </el-tab-pane>

          <el-tab-pane label="Environment" name="environment">
            <div id="environment-content" class="tab-content scrollable">
              <el-button type="primary" @click="triggerFileInput">添加图片</el-button>
              <div v-if="!filteredEnvironmentImages.length && activeTab === 'environment'" class="no-data">暂无图片，请添加。</div>
              <div v-if="filteredEnvironmentImages.length > 0" class="waterfall-gallery">
                <div class="cell-item" v-for="(item, index) in filteredEnvironmentImages" :key="index">
                  <el-card shadow="hover">
                    <el-image
                      class="image"
                      :src="normalizeUrl(item.photoUrl)"
                      :preview-src-list="environmentPreviewList"
                      :initial-index="environmentIndexOf(item.photoUrl)"
                      fit="cover"
                      preview-teleported
                    />
                    <div class="image-actions">
                      <el-button type="danger" size="small" @click="deleteImage(item.photoUrl)">删除</el-button>
                    </div>
                  </el-card>
                </div>
              </div>
            </div>
          </el-tab-pane>

          <el-tab-pane label="Activities" name="activities">
            <div id="activities-content" class="tab-content scrollable">
              <el-button type="primary" @click="triggerFileInput">添加图片</el-button>
              <div v-if="!filteredActivityImages.length && activeTab === 'activities'" class="no-data">暂无图片，请添加。</div>
              <div v-if="filteredActivityImages.length > 0" class="waterfall-gallery">
                <div class="cell-item" v-for="(item, index) in filteredActivityImages" :key="index">
                  <el-card shadow="hover">
                    <el-image
                      class="image"
                      :src="normalizeUrl(item.photoUrl)"
                      :preview-src-list="activityPreviewList"
                      :initial-index="activityIndexOf(item.photoUrl)"
                      fit="cover"
                      preview-teleported
                    />
                    <div class="image-actions">
                      <el-button type="danger" size="small" @click="deleteImage(item.photoUrl)">删除</el-button>
                    </div>
                  </el-card>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>

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
  name: 'DailyImages',
  setup() {
    const foodImages = ref([]);
    const environmentImages = ref([]);
    const activityImages = ref([]);
    const activeTab = ref('food');
    const fileInput = ref(null);
    const token = 'your-token-here'; // TODO: 替换为真实 token

    // 统一规范 URL（把反斜杠替成正斜杠）
    const normalizeUrl = (u) => {
      if (!u) return '';
      const s = Array.isArray(u) ? u[0] : u;
      return String(s).replace(/\\/g, '/');
    };

    const fetchData = async (tab) => {
      try {
        const { data } = await axios.get(`/${tab}/get`);
        if (tab === 'food') {
          foodImages.value = data?.data || [];
        } else if (tab === 'environment') {
          environmentImages.value = data?.data || [];
        } else if (tab === 'activities') {
          activityImages.value = data?.data || [];
        }
      } catch (error) {
        console.error('Error fetching data:', error);
        ElMessage.error('加载失败');
      }
    };

    watch(activeTab, (newTab) => {
      fetchData(newTab);
    });

    // 过滤有效项
    const filteredFoodImages = computed(() =>
      foodImages.value.filter((item) => item && item.photoUrl)
    );
    const filteredEnvironmentImages = computed(() =>
      environmentImages.value.filter((item) => item && item.photoUrl)
    );
    const filteredActivityImages = computed(() =>
      activityImages.value.filter((item) => item && item.photoUrl)
    );

    // 预览列表（同组预览）
    const foodPreviewList = computed(() =>
      filteredFoodImages.value.map((i) => normalizeUrl(i.photoUrl)).filter(Boolean)
    );
    const environmentPreviewList = computed(() =>
      filteredEnvironmentImages.value.map((i) => normalizeUrl(i.photoUrl)).filter(Boolean)
    );
    const activityPreviewList = computed(() =>
      filteredActivityImages.value.map((i) => normalizeUrl(i.photoUrl)).filter(Boolean)
    );

    // 根据某个分组的预览列表，返回一个求索引的函数（方案 B）
    const makeIndexOf = (listRef) => (url) =>
      (listRef.value || []).indexOf(normalizeUrl(url));

    const foodIndexOf = makeIndexOf(foodPreviewList);
    const environmentIndexOf = makeIndexOf(environmentPreviewList);
    const activityIndexOf = makeIndexOf(activityPreviewList);

    const triggerFileInput = () => fileInput.value?.click();

    const handleFileUpload = async () => {
      const file = fileInput.value ? fileInput.value.files[0] : null;
      if (!file) return;

      const formData = new FormData();
      formData.append('image', file);

      try {
        await axios.post(`/${activeTab.value}/add`, formData, {
          headers: { 'Content-Type': 'multipart/form-data', token }
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

    // 删除前二次确认
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
            closeOnClickModal: false,
            closeOnPressEscape: true,
            autofocus: false
          }
        );

        await axios.delete(`/${activeTab.value}/delete?url=${encodeURIComponent(url)}`, {
          headers: { token }
        });

        ElMessage.success('删除成功');
        await fetchData(activeTab.value);
      } catch (err) {
        if (err === 'cancel' || err === 'close') {
          ElMessage.info('已取消删除');
        } else {
          console.error('Error deleting image:', err);
          ElMessage.error('删除图片时出错');
        }
      }
    };

    onMounted(() => {
      fetchData(activeTab.value);
    });

    return {
      foodImages,
      environmentImages,
      activityImages,
      activeTab,
      fileInput,
      triggerFileInput,
      handleFileUpload,
      deleteImage,
      normalizeUrl,

      filteredFoodImages,
      filteredEnvironmentImages,
      filteredActivityImages,

      foodPreviewList,
      environmentPreviewList,
      activityPreviewList,

      // 方案 B 的索引函数
      foodIndexOf,
      environmentIndexOf,
      activityIndexOf,

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
