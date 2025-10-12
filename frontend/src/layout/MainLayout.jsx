import { Layout } from 'antd';

import KakaoMap from './KakaoMap';
import Navbar from './Navbar';
import { useState } from 'react';
import SearchPage from '../pages/SearchPage';
import MyPage from '../pages/MyPage';

const { Sider, Content } = Layout;
const MainLayout = () => {
  const [activeTab, setActiveTab] = useState('search');
  return (
    <>
      <Layout className='h-full'>
        <Sider width={400} theme='light'>
          <Navbar activeTab={activeTab} onChangeTab={setActiveTab} />
          {activeTab === 'search' ? <SearchPage /> : <MyPage />}
        </Sider>
        <Content>
          <KakaoMap />
        </Content>
      </Layout>
    </>
  );
};
export default MainLayout;
