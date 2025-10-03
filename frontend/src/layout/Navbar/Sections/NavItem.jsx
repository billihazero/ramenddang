import React from 'react';
import { Link } from 'react-router-dom';

const routes = [
  { to: '/search', name: '검색' },
  { to: '/my', name: 'MY' },
];

const NavItem = () => {
  return (
    <ul className='h-full flex  justify-around items-center'>
      {routes.map(({ to, name }) => {
        return (
          <li key={name} className='text-xl text-white '>
            <Link to={to}>{name}</Link>
          </li>
        );
      })}
    </ul>
  );
};

export default NavItem;
