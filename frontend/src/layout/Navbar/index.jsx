import React from 'react';
import NavItem from './Sections/NavItem';
import logoUrl from '@assets/images/logo.png';
import SearchInput from './Sections/SearchInput';

const Navbar = () => {
  return (
    <nav className='flex flex-col h-full'>
      {/* logo */}
      <div className='flex justify-around items-center m-2  mt-5 mb-4'>
        <div className='h-15 w-15 '>
          <img src={logoUrl} />
        </div>
        <SearchInput />
      </div>
      <div className='bg-amber-300 h-12'>
        <NavItem />
      </div>
    </nav>
  );
};

export default Navbar;
