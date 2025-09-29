import React from 'react';
import NavItem from './Sections/NavItem';

const Navbar = () => {
  return (
    <nav className='flex flex-col h-full'>
      <div className='h-14'>logo</div>
      <div className='flex-1'>
        <NavItem />
      </div>
    </nav>
  );
};

export default Navbar;
