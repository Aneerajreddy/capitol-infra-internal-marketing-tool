import { useState } from 'react';
import api from '../api/client';

export default function LoginPage({ onDone }) {
  const [mobile, setMobile] = useState('9999999999');
  const [password, setPassword] = useState('Admin@123');

  const submit = async (e) => {
    e.preventDefault();
    const { data } = await api.post('/auth/login', { mobile, password });
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('refreshToken', data.refreshToken);
    onDone();
  };

  return (
    <div className="card" style={{ maxWidth: 420, margin: '40px auto' }}>
      <h3>Admin Login</h3>
      <form onSubmit={submit}>
        <input value={mobile} onChange={(e) => setMobile(e.target.value)} placeholder="Mobile" style={{ width: '100%', marginBottom: 8 }} />
        <input value={password} type="password" onChange={(e) => setPassword(e.target.value)} placeholder="Password" style={{ width: '100%', marginBottom: 8 }} />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}
