import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { prisma } from '../prismaClient.js';
import { hashPassword } from '../utils/password.js';
import { errorRes, successRes } from '../utils/response.js';

//사용자등록
export const createUser = async (req, res, next) => {
  const userData = req.body;
  const { user_id, user_ps } = userData;
  try {
    //필수 값 입력
    if (!user_id || !user_ps) {
      return errorRes(res, '아이디와 패스워드는 필수 값 입니다.', 400);
    }

    //중복체크
    const isExist = await prisma.tb_user.findUnique({
      where: { user_id },
    });
    if (isExist) {
      return errorRes(res, '이미 존재하는 아이디 입니다.', 409);
    }

    //비밀번호 해시
    const hashed = await hashPassword(user_ps);

    //생성
    const newUser = await prisma.tb_user.create({
      data: { ...userData, user_ps: hashed },
    });
    successRes(res, newUser, '사용자 회원가입 성공', 1);
  } catch (err) {
    next(err);
  }
};

//로그인
export const loginUser = async (req, res) => {
  const { user_id, user_ps } = req.body;
  try {
    const user = await prisma.tb_user.findUnique({
      where: { user_id },
    });
    //아이디 조회
    if (!user) {
      return errorRes(res, '존재하지 않는 사용자 입니다.', 404);
    }
    //비밀번호 확인
    const isMatch = await bcrypt.compare(user_ps, user.user_ps);
    if (!isMatch) {
      return errorRes(res, '비밀번호가 일치하지 않습니다.', 401);
    }

    //token 생성
    const payload = {
      user_id,
    };
    const accessToken = jwt.sign(payload, process.env.JWT_SECRET, {
      expiresIn: '1h',
    });
    const { user_ps: _, ...safeUser } = user; //"_"는 일반적으로 사용하지 않는 값을 버릴 때 입력
    successRes(res, { safeUser, accessToken }, '로그인 성공');
  } catch (err) {
    next(err);
  }
};
