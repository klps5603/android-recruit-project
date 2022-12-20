
請使用 Kotlin 實作一個 app 首頁課程列表畫面，並寫文件或註解來解釋你的設計考量。除了指定需求外，你可以自由設計 model 和 UI 來提供更好的體驗。

## 技術規定
- Deployment Target 為 Android 12。
- 可使用第三方 library。
- 請寫文件或註解來解釋你的設計考量。

## 需求
請實作一個在 app 首頁看到的課程列表，需求如下：

#### 資料
- 請設計一個的 Data Loader 的抽象層來提供課程資料。
- 請用專案中提供的 json file 實作上述 Data Loader 的一個實例。

#### UI 設計
- 依照課程當前狀態，顯示不同的標籤
- 課程標題至多兩行
- 本題目不用在意卡片尺寸、顏色、間距等細節，請將重點放在如何排版。（你仍然可以盡量符合示意圖）
<img width="300" alt="CleanShot 2021-12-09 at 10 59 30@2x" src="https://user-images.githubusercontent.com/76472179/145350022-b4624fe0-2612-4fdb-950c-da6898ca4166.png">

## 提交

- 請下載或 fork Hahow Android Engineer 面試題目初始專案。
- 請將成果上傳至 GitHub 並直接提供 repo 連結。

## 操作教學

- 進入首頁會看到所有課程
- 可以點選上方的分類頁籤（全部、募資中、已開課、募資完成），篩選對應的課程
- 滑動列表來瀏覽課程內容（課程圖片、募資狀態、課程名稱、募資進度、倒數天數、影片長度）

## data.json 欄位說明
- successCriteria -> numSoldTickets：目標募資人數
- numSoldTickets：目前募資人數
- status：募資狀態（INCUBATING：募資中、PUBLISHED：已開課、SUCCESS：募資完成）
- proposalDueTime：到期日期
- totalVideoLengthInSeconds：影片長度
- title：課程名稱
- coverImageUrl：圖片 url
