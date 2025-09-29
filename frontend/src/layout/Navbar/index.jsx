import React from 'react';
import NavItem from './Sections/NavItem';
import logoUrl from '@assets/images/logo.png';

const Navbar = () => {
  return (
    <nav className='flex flex-col h-full'>
      {/* logo */}
      <div className='h-20 w-15 m-2'>
        <img src={logoUrl} />
      </div>

      <div>searchinput</div>
      <div>
        <NavItem />
      </div>
    </nav>
  );
};

export default Navbar;
