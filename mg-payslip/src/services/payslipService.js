import http from '../api/http';

export const payslipService = {
  // Metadata comes from the reports microservice
  listMine: () => http.get('/api/reports/payslips/me').then((r) => r.data),
  get: (id) => http.get(`/api/reports/payslips/${id}`).then((r) => r.data),

  // PDF download (blob) from the reports microservice
  downloadPdf: async (payslip) => {
    const res = await http.get(`/api/reports/payslips/${payslip.id}/pdf`, { responseType: 'blob' });
    const url = URL.createObjectURL(res.data);
    const a = document.createElement('a');
    a.href = url;
    a.download = `payslip-${payslip.periodYear}-${String(payslip.periodMonth).padStart(2, '0')}.pdf`;
    a.click();
    URL.revokeObjectURL(url);
  },
};
