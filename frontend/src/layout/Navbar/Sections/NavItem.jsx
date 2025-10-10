import { Button, Flex } from 'antd';
import React from 'react';
import { Link } from 'react-router-dom';

const routes = [
  { to: '/search', name: '검색' },
  { to: '/my', name: 'MY' },
];

const NavItem = () => {
  return (
    <Flex justify='space-around' className='bg-amber-300'>
      {routes.map(({ to, name }) => {
        return (
          <Button
            type='link'
            size='large'
            ghost
            key={name}
            className='!text-white'
          >
            <Link to={to}>{name}</Link>
          </Button>
        );
      })}
    </Flex>
  );
};

export default NavItem;
