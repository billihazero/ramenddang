import { PrismaClient as PrismaClient } from '@prisma/client';

export const prisma = new PrismaClient({
  log: ['query', 'warn', 'error'],
});
