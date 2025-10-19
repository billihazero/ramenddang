import { prisma } from '../prismaClient.js';
import { hashPassword } from '../utils/password.js';
import { successRes } from '../utils/response.js';
export const createUser = async (req, res, next) => {
  const userData = req.body;
  const { user_ps } = userData;
  try {
    const hashed = await hashPassword(user_ps);
    const newUser = await prisma.tb_user.create({
      data: { ...userData, user_ps: hashed },
    });
    successRes(res, newUser, '사용자 회원가입 성공', 1);
  } catch (err) {
    next(err);
  }
};

export const test = async (req, res, next) => {
  console.log('연결 성공');
};
