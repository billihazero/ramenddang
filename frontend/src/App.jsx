import { Outlet, Route, Routes } from 'react-router-dom';
import SearchPage from './pages/SearchPage';
import KakaoMap from './layout/KakaoMap';
import Navbar from './layout/Navbar';
import ProtectedRoutes from './components/Auth/ProtectedRoutes';
import NotAuthRoutes from './components/Auth/NotAuthRoutes';
import LoginPage from './pages/LoginPage';
import MyPage from './pages/MyPage';
import MainLayout from './layout/MainLayout';
import RegisterPage from './pages/RegisterPage';

function App() {
  return (
    <Routes>
      <Route path='/' element={<MainLayout />}>
        <Route index element={<SearchPage />} />
        {/* 로그인한 사람만 갈 수 있는 경로 */}
        {/* <Route element={<ProtectedRoutes />}>
         <Route path='/my' element={<MyPage />} />
       </Route> */}
      </Route>
      {/* 로그인한 사람은 갈 수 없는 경로 */}
      {/* <Route element={<NotAuthRoutes />}> */}
      <Route path='login' element={<LoginPage />} />
      <Route path='register' element={<RegisterPage />} />
      {/* </Route> */}
    </Routes>
  );
}

export default App;

// <Routes>
//     <Route path='/' element={<Layout />}>
//       <Route index element={<SearchPage />} />

//       {/* 로그인한 사람만 갈 수 있는 경로 */}
//       {/* <Route element={<ProtectedRoutes />}>
//         <Route path='/my' element={<MyPage />} />
//       </Route> */}

//       {/* 로그인한 사람은 갈 수 없는 경로 */}
//       {/* <Route element={<NotAuthRoutes />}> */}
//       <Route path='/login' element={<LoginPage />} />
//       {/* </Route> */}
//     </Route>
//   </Routes>
