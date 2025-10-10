import { Layout } from 'antd';
import KakaoMap from './KakaoMap';
import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';

const { Sider, Content } = Layout;
const MainLayout = () => {
  return (
    <>
      <Layout className='h-full'>
        <Sider width={400} theme='light'>
          <Navbar />
          <Outlet />
        </Sider>
        <Content>
          <KakaoMap />
        </Content>
      </Layout>
    </>
  );
};
export default MainLayout;
