import { Outlet, Route, Routes } from 'react-router-dom';
import SearchPage from './pages/SearchPage';
import KakaoMap from './layout/KakaoMap';
import Navbar from './layout/Navbar';

function Layout() {
  return (
    <div className='flex h-screen justify-between'>
      <Navbar />
      <aside className='w-[360px] border-r bg-white overflow-auto'>
        <Outlet />
      </aside>
      <section className='flex-1 relative'>
        <KakaoMap />
      </section>
    </div>
  );
}
function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route index element={<SearchPage />} />
      </Route>
    </Routes>
  );
}

export default App;
